package com.strategy.assistant.data.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CocScreenshotReportFormatterTest {
 @Test fun formatsObservationsAndConfidence() {
  val raw = """{"screenshot_context":"Enemy village","observations":[{"category":"collector","name":"Elixir Collector","relative_location":"top-left","confidence":"high","visual_evidence":"Purple liquid visible."},{"category":"defense","name":"Unknown defense","relative_location":"center","confidence":"uncertain","visual_evidence":"Partially obscured."}],"resource_targets":["Collectors on the left"],"recommended_entry":{"side":"left","reason":"Collectors appear exposed.","confidence":"medium"},"attack_sequence":["Create a funnel"],"risks":["Central defenses"],"limitations":["Some buildings are obscured"],"next_question":"What army do you have?"}"""
  val result = CocScreenshotReportFormatter.formatOrFallback(raw)
  assertTrue(result.contains("Village screenshot report")); assertTrue(result.contains("Elixir Collector"))
  assertTrue(result.contains("High** confidence")); assertTrue(result.contains("Unclear** confidence"))
  assertTrue(result.contains("Recommended entry")); assertTrue(result.contains("What army do you have?"))
 }
 @Test fun malformedAndUnrecognizedResponsesFallBack() {
  val plain = "Normal answer"; assertEquals(plain, CocScreenshotReportFormatter.formatOrFallback(plain))
  val unrelated = """{"message":"hello"}"""; assertEquals(unrelated, CocScreenshotReportFormatter.formatOrFallback(unrelated))
 }
 @Test fun acceptsFencedJson() {
  val raw = """```json
{"screenshot_context":"Village","observations":[]}
```"""
  assertTrue(CocScreenshotReportFormatter.formatOrFallback(raw).contains("No reliable building observations"))
 }
}
