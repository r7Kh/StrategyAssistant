package com.strategy.assistant.data.ai

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AiProviderStoreInstrumentedTest {
    private lateinit var context: Context
    private lateinit var preferences: android.content.SharedPreferences
    private lateinit var store: AiProviderStore

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        preferences = context.getSharedPreferences("ai_provider_secure", Context.MODE_PRIVATE)
        preferences.edit().clear().commit()
        store = AiProviderStore(context)
    }

    @After
    fun tearDown() {
        preferences.edit().clear().commit()
        store.clearKey(AiProvider.GEMINI)
        store.clearKey(AiProvider.OPENAI)
    }

    @Test
    fun apiKeyIsEncryptedAtRestAndCanBeRestored() {
        val apiKey = "integration-test-secret-key"
        store.save(AiProviderConfig(
            provider = AiProvider.GEMINI,
            apiKey = apiKey,
            model = AiProvider.GEMINI.defaultModel,
            baseUrl = AiProvider.GEMINI.defaultBaseUrl,
        ))

        val encryptedValue = preferences.getString("secret_gemini", null)

        assertTrue(store.hasKey(AiProvider.GEMINI))
        assertNotEquals(apiKey, encryptedValue)
        assertEquals(apiKey, store.load().apiKey)
    }

    @Test
    fun invalidCiphertextIsNotReturnedAsAnApiKey() {
        preferences.edit()
            .putString("provider", AiProvider.GEMINI.id)
            .putString("secret_gemini", "not-valid-ciphertext")
            .commit()

        assertFalse(store.hasKey(AiProvider.GEMINI))
        assertEquals("", store.load().apiKey)
    }

    @Test
    fun clearingProviderKeyRemovesStoredCiphertext() {
        store.save(AiProviderConfig(
            provider = AiProvider.GEMINI,
            apiKey = "integration-test-secret-key",
        ))

        store.clearKey(AiProvider.GEMINI)

        assertFalse(store.hasKey(AiProvider.GEMINI))
        assertFalse(preferences.contains("secret_gemini"))
    }

    @Test
    fun providerKeysRemainIsolatedAcrossProviderSwitchesAndStoreRecreation() {
        store.save(AiProviderConfig(
            provider = AiProvider.GEMINI,
            apiKey = "gemini-integration-key",
        ))
        store.save(AiProviderConfig(
            provider = AiProvider.OPENAI,
            apiKey = "openai-integration-key",
        ))

        val restoredOpenAi = AiProviderStore(context).load()
        assertEquals(AiProvider.OPENAI, restoredOpenAi.provider)
        assertEquals("openai-integration-key", restoredOpenAi.apiKey)

        store.save(AiProviderConfig(provider = AiProvider.GEMINI))
        val restoredGemini = AiProviderStore(context).load()
        assertEquals(AiProvider.GEMINI, restoredGemini.provider)
        assertEquals("gemini-integration-key", restoredGemini.apiKey)
    }
}
