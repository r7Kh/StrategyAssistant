package com.strategy.assistant.data.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CocScreenshotReportFormatterTest {
    @Test fun formatsObservationsAndConfidence() {
        val raw = """{"screenshot_context":"Enemy village","observations":[{"category":"collector","name":"Elixir Collector","relative_location":"top-left","confidence":"high","visual_evidence":"Purple liquid visible."},{"category":"defense","name":"Unknown defense","relative_location":"center","confidence":"uncertain","visual_evidence":"Partially obscured."}],"resource_targets":["Collectors on the left"],"recommended_entry":{"side":"left","reason":"Collectors appear exposed.","confidence":"medium"},"attack_sequence":["Create a funnel"],"risks":["Central defenses"],"limitations":["Some buildings are obscured"],"next_question":"What army do you have?"}"""
        val result = CocScreenshotReportFormatter.formatOrFallback(raw)
        assertTrue(result.contains("Village screenshot report"))
        assertTrue(result.contains("Elixir Collector"))
        assertTrue(result.contains("**High confidence**"))
        assertTrue(result.contains("**Unclear confidence**"))
        assertTrue(result.contains("Recommended entry"))
        assertTrue(result.contains("What army do you have?"))
    }

    @Test fun malformedAndUnrecognizedResponsesFallBack() {
        val plain = "Normal answer"
        assertEquals(plain, CocScreenshotReportFormatter.formatOrFallback(plain))
        val unrelated = """{"message":"hello"}"""
        assertEquals(unrelated, CocScreenshotReportFormatter.formatOrFallback(unrelated))
        val malformed = """{"screenshot_context":"Village"""
        assertEquals(malformed, CocScreenshotReportFormatter.formatOrFallback(malformed))
    }

    @Test fun acceptsFencedJsonWithUppercaseFence() {
        val raw = """```JSON
{"screenshot_context":"Village","observations":[]}
```"""
        assertTrue(CocScreenshotReportFormatter.formatOrFallback(raw).contains("No reliable building observations"))
    }

    @Test fun extractsJsonWhenProviderAddsIntroductoryText() {
        val raw = "Analysis follows:\n```json\n{\"screenshot_context\":\"قرية العدو\",\"observations\":[]}\n```\nDone."
        val result = CocScreenshotReportFormatter.formatOrFallback(raw)
        assertTrue(result.contains("قرية العدو"))
        assertFalse(result.contains("Analysis follows"))
    }

    @Test fun handlesMissingFieldsNullsAndArabicContent() {
        val raw = """{"screenshot_context":"قرية","observations":[null,{"name":"مخزن الذهب","relative_location":null,"confidence":"medium","visual_evidence":"ظاهر بوضوح"}],"resource_targets":[null,"الذهب"]}"""
        val result = CocScreenshotReportFormatter.formatOrFallback(raw)
        assertTrue(result.contains("مخزن الذهب"))
        assertTrue(result.contains("الذهب"))
        assertFalse(result.contains("null"))
        assertTrue(result.contains("Location unclear"))
    }
}
