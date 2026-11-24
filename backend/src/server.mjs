import { createServer } from "node:http";
import { GoogleGenAI } from "@google/genai";

const PORT = Number.parseInt(process.env.PORT ?? "8080", 10);
const HOST = process.env.HOST ?? "0.0.0.0";
const MODEL = process.env.GEMINI_MODEL ?? "gemini-3.8-flash";
const API_KEY = process.env.GEMINI_API_KEY;
const MAX_BODY_BYTES = 32 * 1024;
const MAX_MESSAGE_CHARS = 8_000;
const MAX_HISTORY_ITEMS = 12;
const WINDOW_MS = 60_000;
const MAX_REQUESTS_PER_WINDOW = 20;

const SYSTEM_INSTRUCTION = `
You are STRATEGY ASSISTANT, a thoughtful gaming strategy assistant.
Help users understand game situations, compare options, plan tactics, and review performance.
Ask a concise clarifying question when essential game context is missing.
Never invent game-specific facts, patch notes, statistics, or live data. Clearly label assumptions.
Reply in the same language as the user unless they ask otherwise.
Use clear structure and practical recommendations. Do not claim to control the user's game.
`;

const ai = API_KEY ? new GoogleGenAI({ apiKey: API_KEY }) : null;
const rateBuckets = new Map();

function sendJson(response, status, payload) {
  response.writeHead(status, {
    "content-type": "application/json; charset=utf-8",
    "cache-control": "no-store",
    "x-content-type-options": "nosniff"
  });
  response.end(JSON.stringify(payload));
}

function isRateLimited(request) {
  const forwarded = request.headers["x-forwarded-for"];
  const clientKey = (typeof forwarded === "string" ? forwarded.split(",")[0] : null)
    ?? request.socket.remoteAddress
    ?? "unknown";
  const now = Date.now();
  const bucket = rateBuckets.get(clientKey);

  if (!bucket || now - bucket.startedAt >= WINDOW_MS) {
    rateBuckets.set(clientKey, { startedAt: now, count: 1 });
    return false;
  }

  bucket.count += 1;
  return bucket.count > MAX_REQUESTS_PER_WINDOW;
}

async function readJson(request) {
  let size = 0;
  const chunks = [];
  for await (const chunk of request) {
    size += chunk.length;
    if (size > MAX_BODY_BYTES) {
      const error = new Error("Request body is too large.");
      error.statusCode = 413;
      throw error;
    }
    chunks.push(chunk);
  }
  try {
    return JSON.parse(Buffer.concat(chunks).toString("utf8"));
  } catch {
    const error = new Error("Request body must be valid JSON.");
    error.statusCode = 400;
    throw error;
  }
}

function normalizeConversation(body) {
  if (!body || typeof body !== "object") {
    throw Object.assign(new Error("A JSON request body is required."), { statusCode: 400 });
  }

  const message = typeof body.message === "string" ? body.message.trim() : "";
  if (!message) {
    throw Object.assign(new Error("Message cannot be empty."), { statusCode: 400 });
  }
  if (message.length > MAX_MESSAGE_CHARS) {
    throw Object.assign(new Error("Message is too long."), { statusCode: 413 });
  }

  const history = Array.isArray(body.history) ? body.history.slice(-MAX_HISTORY_ITEMS) : [];
  const contents = history
    .filter((item) =>
      item
      && (item.role === "user" || item.role === "model")
      && typeof item.text === "string"
      && item.text.trim().length > 0
      && item.text.length <= MAX_MESSAGE_CHARS
    )
    .map((item) => ({
      role: item.role,
      parts: [{ text: item.text.trim() }]
    }));

  contents.push({ role: "user", parts: [{ text: message }] });
  return contents;
}

const server = createServer(async (request, response) => {
  const url = new URL(request.url ?? "/", `http://${request.headers.host ?? "localhost"}`);

  if (request.method === "GET" && url.pathname === "/health") {
    return sendJson(response, 200, {
      status: "ok",
      provider: "gemini",
      model: MODEL,
      configured: Boolean(ai)
    });
  }

  if (request.method !== "POST" || url.pathname !== "/v1/chat") {
    return sendJson(response, 404, { error: { code: "NOT_FOUND", message: "Endpoint not found." } });
  }

  if (isRateLimited(request)) {
    return sendJson(response, 429, {
      error: { code: "RATE_LIMITED", message: "Too many requests. Please try again shortly." }
    });
  }

  if (!ai) {
    return sendJson(response, 503, {
      error: { code: "AI_NOT_CONFIGURED", message: "The Gemini backend is not configured yet." }
    });
  }

  try {
    const body = await readJson(request);
    const contents = normalizeConversation(body);
    const result = await ai.models.generateContent({
      model: MODEL,
      contents,
      config: {
        systemInstruction: SYSTEM_INSTRUCTION,
        temperature: 0.65,
        maxOutputTokens: 1200
      }
    });

    const reply = result.text?.trim();
    if (!reply) {
      return sendJson(response, 502, {
        error: { code: "EMPTY_MODEL_RESPONSE", message: "Gemini returned an empty response." }
      });
    }

    return sendJson(response, 200, { reply, provider: "gemini", model: MODEL });
  } catch (error) {
    const status = Number.isInteger(error?.statusCode) ? error.statusCode : 502;
    if (status === 400 || status === 413) {
      return sendJson(response, status, { error: { code: "INVALID_REQUEST", message: error.message } });
    }
    // Provider details and credentials are deliberately not returned to the app.
    console.error("Gemini request failed:", error?.status ?? error?.name ?? "unknown error");
    return sendJson(response, 502, {
      error: { code: "AI_PROVIDER_ERROR", message: "The AI service could not complete the request." }
    });
  }
});

server.listen(PORT, HOST, () => {
  console.log(`Strategy Assistant AI backend listening on ${HOST}:${PORT}`);
  console.log(`Provider: Gemini | Model: ${MODEL} | API key configured: ${Boolean(ai)}`);
});
