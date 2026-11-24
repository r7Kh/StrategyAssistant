# Strategy Assistant AI Backend

A provider-isolated Node.js API for the Android app. Gemini is the first provider; the Android app never receives the Gemini API key.

## Requirements

- Node.js 20+
- A Gemini API key created in Google AI Studio

## Local setup

1. Copy `.env.example` to `.env`.
2. Put the key in `GEMINI_API_KEY` in the local `.env` file.
3. Run `npm install`.
4. Run `npm run dev`.

The server listens on port 8080 by default.

## Endpoints

- `GET /health`: provider/model/configuration status; never returns secrets.
- `POST /v1/chat`: accepts `{ "message": "...", "history": [{ "role": "user|model", "text": "..." }] }`.

## Configuration

- `GEMINI_API_KEY`: required secret; never commit it.
- `GEMINI_MODEL`: defaults to `gemini-3.8-flash`; configurable without changing Android code.
- `PORT`, `HOST`: server bind configuration.

## Security baseline

- The Gemini key is read only from the backend environment.
- Request bodies and message lengths are capped.
- A basic in-memory per-IP request limit is enabled.
- Provider errors are not forwarded verbatim to clients.
- Do not deploy this as a public multi-user service yet. Before public release, add user authentication, durable per-user quotas, abuse monitoring, and a managed secret store. The in-memory limiter is a development safeguard, not a distributed production control.

## Future providers

Keep provider-specific SDK calls behind the backend boundary. Add a provider adapter that returns the same internal reply shape; keep the Android contract `POST /v1/chat` unchanged.
