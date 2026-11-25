package com.strategy.assistant.data.ai

import org.json.JSONArray
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Offline regression corpus for attack-plan formatting and deterministic sanity checks.
 * These cases verify app behavior; they do not measure the external model's strategic accuracy.
 */
class CocAttackEvaluationDatasetTest {

    @Test
    fun curatedScenariosMatchExpectedWarningsAndRenderedEvidence() {
        val stream = javaClass.classLoader?.getResourceAsStream("coc_attack_evaluation_cases.json")
        requireNotNull(stream) { "Missing coc_attack_evaluation_cases.json test resource." }
        val raw = stream.bufferedReader().use { it.readText() }
        val cases = JSONArray(raw)
        assertTrue("The evaluation corpus should not be empty.", cases.length() >= 5)

        for (index in 0 until cases.length()) {
            val scenario = cases.getJSONObject(index)
            val id = scenario.getString("id")
            val plan = scenario.getJSONObject("plan").toString()
            val expectedWarnings = scenario.getJSONArray("expected_warnings")
            val expectedContains = scenario.getJSONArray("expected_output_contains")
            val expectedNotContains = scenario.getJSONArray("expected_output_not_contains")

            val warnings = CocAttackPlanValidator.validate(plan)
            val rendered = CocAttackPlanFormatter.formatOrFallback(plan)

            for (warningIndex in 0 until expectedWarnings.length()) {
                val expected = expectedWarnings.getString(warningIndex)
                assertTrue("$id should report warning '$expected'. Warnings: $warnings",
                    warnings.any { it.contains(expected, ignoreCase = true) })
            }
            if (expectedWarnings.length() == 0) {
                assertTrue("$id should have no validation warnings. Warnings: $warnings", warnings.isEmpty())
            }
            for (textIndex in 0 until expectedContains.length()) {
                val expected = expectedContains.getString(textIndex)
                assertTrue("$id should render '$expected'.", rendered.contains(expected, ignoreCase = true))
            }
            for (textIndex in 0 until expectedNotContains.length()) {
                val unexpected = expectedNotContains.getString(textIndex)
                assertFalse("$id must not render '$unexpected'.", rendered.contains(unexpected, ignoreCase = true))
            }
        }
        assertEquals("The corpus format should remain stable.", 5, cases.length())
    }
}
