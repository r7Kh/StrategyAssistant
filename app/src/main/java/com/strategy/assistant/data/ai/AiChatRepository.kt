package com.strategy.assistant.data.ai

import com.strategy.assistant.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Transport boundary for AI chat.
 *
 * The Android app only knows the Strategy Assistant backend URL. Provider names,
 * model identifiers, and provider credentials stay on the server.
 */
data class AiChatMessage(
    val role: String,
    val text: String
)

data class AiChatReply(
    val text: String,
    val provider: String,
    val model: String
)

class AiBackendException(message: String, val httpCode: Int? = null) : Exception(message)

class AiChatRepository(
    private val backendBaseUrl: String = BuildConfig.AI_BACKEND_URL
) {
    suspend fun sendMessage(
        message: String,
        history: List<AiChatMessage> = emptyList()
    ): AiChatReply = withContext(Dispatchers.IO) {
        val baseUrl = backendBaseUrl.trim().trimEnd('/')
        if (baseUrl.isBlank()) {
            throw AiBackendException(
                "AI backend URL is not configured. Set AI_BACKEND_URL in your local Gradle properties."
            )
        }
        if (!baseUrl.startsWith("https://") && !baseUrl.startsWith("http://10.0.2.2")) {
            throw AiBackendException("The AI backend must use HTTPS outside the Android emulator.")
        }

        val historyJson = JSONArray()
        history.takeLast(12).forEach { item ->
            if ((item.role == "user" || item.role == "model") && item.text.isNotBlank()) {
                historyJson.put(
                    JSONObject()
                        .put("role", item.role)
                        .put("text", item.text.take(8_000))
                )
            }
        }

        val payload = JSONObject()
            .put("message", message.trim().take(8_000))
            .put("history", historyJson)
            .toString()

        val connection = (URL("$baseUrl/v1/chat").openConnection() as HttpURLConnection)
        try {
            connection.requestMethod = "POST"
            connection.connectTimeout = 15_000
            connection.readTimeout = 60_000
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.setRequestProperty("Accept", "application/json")
            connection.outputStream.use { output ->
                output.write(payload.toByteArray(Charsets.UTF_8))
            }

            val status = connection.responseCode
            val stream = if (status in 200..299) connection.inputStream else connection.errorStream
            val responseText = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }.orEmpty()
            val response = runCatching { JSONObject(responseText) }.getOrElse {
                throw AiBackendException("The AI backend returned an invalid response.", status)
            }

            if (status !in 200..299) {
                val messageFromServer = response.optJSONObject("error")
                    ?.optString("message")
                    ?.takeIf { it.isNotBlank() }
                    ?: "AI request failed (HTTP $status)."
                throw AiBackendException(messageFromServer, status)
            }

            val reply = response.optString("reply").trim()
            if (reply.isBlank()) throw AiBackendException("The AI backend returned an empty reply.", status)

            AiChatReply(
                text = reply,
                provider = response.optString("provider", "unknown"),
                model = response.optString("model", "unknown")
            )
        } finally {
            connection.disconnect()
        }
    }
}
