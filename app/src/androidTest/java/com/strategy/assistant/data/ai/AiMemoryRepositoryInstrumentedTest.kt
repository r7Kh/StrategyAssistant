package com.strategy.assistant.data.ai

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AiMemoryRepositoryInstrumentedTest {
    private lateinit var context: Context
    private lateinit var repository: AiMemoryRepository

    @Before
    fun setUp() = runBlocking {
        context = ApplicationProvider.getApplicationContext()
        repository = AiMemoryRepository(context)
        repository.clearAll()
        repository.setEnabled(true)
    }

    @After
    fun tearDown() = runBlocking {
        repository.clearAll()
        repository.setEnabled(true)
    }

    @Test
    fun manuallyAddedMemorySurvivesRepositoryRecreation() = runBlocking {
        repository.add("Gaming", "I play Clash of Clans")

        val restored = AiMemoryRepository(context).snapshot.first()

        assertTrue(restored.enabled)
        assertEquals(1, restored.items.size)
        assertEquals("Gaming", restored.items.single().category)
        assertEquals("I play Clash of Clans", restored.items.single().content)
        assertTrue(restored.toPromptContext().contains("I play Clash of Clans"))
    }

    @Test
    fun disablingMemoryStopsAutomaticCaptureAndPromptContext() = runBlocking {
        repository.setEnabled(false)
        repository.rememberExplicitFacts("My name is Ahmad. I play Clash of Clans.")

        val disabled = AiMemoryRepository(context).snapshot.first()

        assertFalse(disabled.enabled)
        assertTrue(disabled.items.isEmpty())
        assertEquals("", disabled.toPromptContext())
    }

    @Test
    fun reEnablingMemoryAllowsAutomaticCaptureAgain() = runBlocking {
        repository.setEnabled(false)
        repository.rememberExplicitFacts("I play Clash of Clans.")
        repository.setEnabled(true)
        repository.rememberExplicitFacts("I play Clash of Clans.")

        val enabled = repository.snapshot.first()

        assertTrue(enabled.enabled)
        assertTrue(enabled.items.any { it.content == "Clash of Clans" })
    }

    @Test
    fun clearAllRemovesSavedItemsWithoutDisablingMemory() = runBlocking {
        repository.add("Gaming", "I play Clash of Clans")
        repository.clearAll()

        val cleared = repository.snapshot.first()

        assertTrue(cleared.enabled)
        assertTrue(cleared.items.isEmpty())
    }
}
