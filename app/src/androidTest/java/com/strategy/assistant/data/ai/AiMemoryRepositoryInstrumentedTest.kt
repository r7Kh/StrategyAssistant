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

    @Test
    fun updateChangesOnlyTheSelectedMemory() = runBlocking {
        repository.add("Gaming", "I play Clash of Clans")
        repository.add("Personal", "My name is Ahmad")
        val before = repository.snapshot.first()
        val target = before.items.first { it.category == "Gaming" }

        repository.update(target.id, "Games", "I play strategy games")

        val updated = repository.snapshot.first()
        assertEquals(2, updated.items.size)
        assertTrue(updated.items.any { it.id == target.id && it.category == "Games" && it.content == "I play strategy games" })
        assertTrue(updated.items.any { it.category == "Personal" && it.content == "My name is Ahmad" })
    }

    @Test
    fun deleteRemovesOnlyTheSelectedMemory() = runBlocking {
        repository.add("Gaming", "I play Clash of Clans")
        repository.add("Personal", "My name is Ahmad")
        val target = repository.snapshot.first().items.first { it.category == "Gaming" }

        repository.delete(target.id)

        val remaining = repository.snapshot.first().items
        assertEquals(1, remaining.size)
        assertFalse(remaining.any { it.id == target.id })
        assertEquals("My name is Ahmad", remaining.single().content)
    }

    @Test
    fun duplicateMemoryIsNotStoredTwiceIgnoringCase() = runBlocking {
        repository.add("Gaming", "I play Clash of Clans")
        repository.add("Other", "i PLAY clash of clans")

        val items = repository.snapshot.first().items
        assertEquals(1, items.size)
        assertEquals("Gaming", items.single().category)
    }

    @Test
    fun memoryCollectionIsCappedAtOneHundredItems() = runBlocking {
        repeat(101) { index ->
            repository.add("Test", "Memory item number $index")
        }

        val items = repository.snapshot.first().items
        assertEquals(100, items.size)
        assertEquals("Memory item number 100", items.first().content)
    }
}
