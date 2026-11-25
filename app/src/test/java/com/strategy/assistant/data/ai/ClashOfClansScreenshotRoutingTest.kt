package com.strategy.assistant.data.ai

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ClashOfClansScreenshotRoutingTest {
    @Test
    fun enablesStructuredScreenshotAnalysisForExplicitClashOfClansContext() {
        assertTrue(shouldUseClashOfClansScreenshotAnalysis(true, "Analyze this Clash of Clans village"))
        assertTrue(shouldUseClashOfClansScreenshotAnalysis(true, "حلل هجوم كلاش اوف كلانس"))
    }

    @Test
    fun keepsGenericImagesOutOfClashOfClansReportPipeline() {
        assertFalse(shouldUseClashOfClansScreenshotAnalysis(true, "Describe this landscape photo"))
        assertFalse(shouldUseClashOfClansScreenshotAnalysis(false, "Analyze this Clash of Clans village"))
    }

    @Test
    fun usesConversationContextWhenCurrentPromptIsBrief() {
        val history = listOf("user" to "I play Clash of Clans")
        assertTrue(shouldUseClashOfClansScreenshotAnalysis(true, "What do you see here?", history))
    }

    @Test
    fun doesNotTreatCocAsAnArbitrarySubstring() {
        assertFalse(isClashOfClansContext("Please describe cocoa beans", emptyList(), ""))
        assertTrue(isClashOfClansContext("I need a CoC farming plan", emptyList(), ""))
    }
}