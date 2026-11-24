package com.strategy.assistant.data.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class AiReply(val text: String, val provider: String, val model: String)
class AiProviderException(message: String, val statusCode: Int? = null) : Exception(message)

class DirectAiClient {
    suspend fun send(config: AiProviderConfig, prompt: String, history: List<Pair<String, String>> = emptyList()): AiReply =
        withContext(Dispatchers.IO) {
            require(config.apiKey.isNotBlank()) { "Add your provider API key in AI Control Panel first." }
            require(prompt.isNotBlank()) { "Message cannot be empty." }
            val model = config.model.ifBlank { config.provider.defaultModel }
            val base = config.baseUrl.trim().trimEnd('/')
            require(base.startsWith("https://")) { "Provider URL must use HTTPS." }

            val endpoint: String
            val body: JSONObject
            when (config.provider) {
                AiProvider.GEMINI -> {
                    endpoint = base + "/v1beta/models/" + model + ":generateContent?key=" + config.apiKey
                    val parts = JSONArray()
                    history.takeLast(10).forEach { (_, text) -> parts.put(JSONObject().put("text", text)) }
                    parts.put(JSONObject().put("text", prompt))
                    body = JSONObject().put("contents", JSONArray().put(JSONObject().put("parts", parts)))
                        .put("generationConfig", JSONObject().put("temperature", 0.65).put("maxOutputTokens", 1200))
                }
                AiProvider.ANTHROPIC -> {
                    endpoint = base + "/v1/messages"
                    val messages = JSONArray()
                    history.takeLast(10).forEach { (role, text) ->
                        messages.put(JSONObject().put("role", if (role == "assistant") "assistant" else "user").put("content", text))
                    }
                    messages.put(JSONObject().put("role", "user").put("content", prompt))
                    body = JSONObject().put("model", model).put("max_tokens", 1200).put("messages", messages)
                }
                else -> {
                    endpoint = base + "/v1/chat/completions"
                    val messages = JSONArray()
                    messages.put(JSONObject().put("role", "system").put("content",
                        "You are STRATEGY ASSISTANT, a gaming strategy assistant. Reply in the user's language. Never invent game facts."))
                    history.takeLast(10).forEach { (role, text) ->
                        messages.put(JSONObject().put("role", if (role == "assistant") "assistant" else "user").put("content", text))
                    }
                    messages.put(JSONObject().put("role", "user").put("content", prompt))
                    body = JSONObject().put("model", model).put("messages", messages).put("temperature", 0.65)
                }
            }

            val connection = URL(endpoint).openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "POST"
                connection.connectTimeout = 15000
                connection.readTimeout = 60000
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                when (config.provider) {
                    AiProvider.GEMINI -> Unit
                    AiProvider.ANTHROPIC -> {
                        connection.setRequestProperty("x-api-key", config.apiKey)
                        connection.setRequestProperty("anthropic-version", "2023-06-01")
                    }
                    else -> connection.setRequestProperty("Authorization", "Bearer " + config.apiKey)
                }
                if (config.provider == AiProvider.OPENROUTER) {
                    connection.setRequestProperty("HTTP-Referer", "https://strategyassistant.app")
                    connection.setRequestProperty("X-Title", "STRATEGY ASSISTANT")
                }
                connection.outputStream.use { it.write(body.toString().toByteArray(Charsets.UTF_8)) }
                val status = connection.responseCode
                val stream = if (status in 200..299) connection.inputStream else connection.errorStream
                val raw = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }.orEmpty()
                if (status !in 200..299) throw AiProviderException(
                    "Provider request failed (HTTP " + status + "). Check the API key, model and account quota.", status)
                val json = JSONObject(raw)
                val answer = when (config.provider) {
                    AiProvider.GEMINI -> json.optJSONArray("candidates")?.optJSONObject(0)
                        ?.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0)?.optString("text")
                    AiProvider.ANTHROPIC -> json.optJSONArray("content")?.optJSONObject(0)?.optString("text")
                    else -> json.optJSONArray("choices")?.optJSONObject(0)?.optJSONObject("message")?.optString("content")
                }.orEmpty().trim()
                if (answer.isBlank()) throw AiProviderException("The provider returned an empty response.", status)
                AiReply(answer, config.provider.title, model)
            } catch (error: AiProviderException) {
                throw error
            } catch (error: Exception) {
                throw AiProviderException(error.message ?: "Could not connect to the AI provider.")
            } finally {
                connection.disconnect()
            }
        }
}
