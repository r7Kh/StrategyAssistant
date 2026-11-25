package com.strategy.assistant.data.ai

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

enum class AiProvider(val id: String, val title: String, val defaultModel: String, val defaultBaseUrl: String) {
    GEMINI("gemini", "Google Gemini", "gemini-3.8-flash", "https://generativelanguage.googleapis.com"),
    OPENAI("openai", "OpenAI", "gpt-4.1-mini", "https://api.openai.com"),
    ANTHROPIC("anthropic", "Anthropic Claude", "claude-3-5-haiku-latest", "https://api.anthropic.com"),
    OPENROUTER("openrouter", "OpenRouter", "openai/gpt-4.1-mini", "https://openrouter.ai/api"),
    CUSTOM("custom", "Custom OpenAI-compatible", "", "");

    companion object {
        fun fromId(id: String) = entries.firstOrNull { it.id == id } ?: GEMINI
    }
}

data class AiProviderConfig(
    val provider: AiProvider = AiProvider.GEMINI,
    val apiKey: String = "",
    val model: String = AiProvider.GEMINI.defaultModel,
    val baseUrl: String = AiProvider.GEMINI.defaultBaseUrl,
)

class AiProviderStore(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("ai_provider_secure", Context.MODE_PRIVATE)
    private val alias = "strategy_assistant_ai_key_v1"

    fun load(): AiProviderConfig {
        val provider = AiProvider.fromId(prefs.getString("provider", "gemini").orEmpty())
        val encrypted = prefs.getString(secretName(provider), null)
        val key = encrypted?.let { runCatching { decrypt(it) }.getOrNull() }.orEmpty()
        return AiProviderConfig(provider, key,
            prefs.getString("model", provider.defaultModel).orEmpty(),
            prefs.getString("base_url", provider.defaultBaseUrl).orEmpty())
    }

    fun save(config: AiProviderConfig) {
        val editor = prefs.edit().putString("provider", config.provider.id)
            .putString("model", config.model.trim())
            .putString("base_url", config.baseUrl.trim().trimEnd('/'))
        if (config.apiKey.isNotBlank()) editor.putString(secretName(config.provider), encrypt(config.apiKey.trim()))
        editor.remove("web_research_enabled")
        editor.apply()
    }

    fun hasKey(provider: AiProvider): Boolean = prefs.getString(secretName(provider), null)?.let { runCatching { decrypt(it) }.isSuccess } == true

    fun clearKey(provider: AiProvider = load().provider) { prefs.edit().remove(secretName(provider)).apply() }

    private fun secretName(provider: AiProvider) = "secret_" + provider.id

    private fun getKey(): SecretKey {
        val store = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        (store.getKey(alias, null) as? SecretKey)?.let { return it }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        generator.init(KeyGenParameterSpec.Builder(alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(true).build())
        return generator.generateKey()
    }

    private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        return Base64.encodeToString(cipher.iv + cipher.doFinal(value.toByteArray(Charsets.UTF_8)), Base64.NO_WRAP)
    }

    private fun decrypt(value: String): String {
        val raw = Base64.decode(value, Base64.NO_WRAP)
        require(raw.size > 12) { "Invalid encrypted API key" }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(128, raw.copyOfRange(0, 12)))
        return String(cipher.doFinal(raw.copyOfRange(12, raw.size)), Charsets.UTF_8)
    }
}
