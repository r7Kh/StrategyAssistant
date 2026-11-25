package com.strategy.assistant.data.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AiMemoryExtractionTest {
    @Test
    fun extractsExplicitEnglishFacts() {
        val facts = extractExplicitMemoryFacts("My name is Ahmad. I play Clash of Clans. I prefer farming strategies.")
        assertTrue(facts.contains("Personal" to "Ahmad"))
        assertTrue(facts.contains("Gaming" to "Clash of Clans"))
        assertTrue(facts.contains("Preferences" to "farming strategies"))
    }

    @Test
    fun extractsExplicitArabicFacts() {
        val facts = extractExplicitMemoryFacts("اسمي أحمد. أنا ألعب كلاش أوف كلانس. بفضّل الهجمات السريعة.")
        assertTrue(facts.contains("Personal" to "أحمد"))
        assertTrue(facts.contains("Gaming" to "كلاش أوف كلانس"))
        assertTrue(facts.contains("Preferences" to "الهجمات السريعة"))
    }

    @Test
    fun ignoresMessagesWithoutSupportedExplicitPhrases() {
        assertTrue(extractExplicitMemoryFacts("Can you recommend a farming army?").isEmpty())
        assertTrue(extractExplicitMemoryFacts("I attacked a base and got 500k gold.").isEmpty())
    }

    @Test
    fun ignoresBlankInput() {
        assertEquals(emptyList<Pair<String, String>>(), extractExplicitMemoryFacts("  \n "))
    }
}
