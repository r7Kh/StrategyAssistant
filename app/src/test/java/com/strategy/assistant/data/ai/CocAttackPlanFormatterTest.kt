package com.strategy.assistant.data.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CocAttackPlanFormatterTest {

    @Test
    fun formatsStructuredPlanAndDeploymentPhases() {
        val raw = """{
            "title":"Collector-focused raid",
            "strategy_summary":"Use a cautious edge entry.",
            "resource_goal":"Elixir",
            "target_criteria":["Exposed collectors"],
            "base_observations":[{"item":"Collector","location":"north edge","confidence":"high","evidence":"Clearly visible outside walls"}],
            "army":[{"unit":"Barbarian","quantity":"12","confidence":"high","reason":"Confirmed by player"}],
            "entry":{"side":"North","rationale":"Collectors are exposed there.","confidence":"medium"},
            "phases":[{"name":"Funnel","actions":["Deploy a small group"],"timing":"Before main force"}],
            "spell_timing":["Use spells only if the main group enters danger."],
            "risks":["Splash defense coverage"],
            "skip_if":["Collectors are empty"],
            "fallback":"Switch to another exposed edge.",
            "assumptions":["Army details were supplied by player."],
            "missing_info":["Exact spell inventory"]
        }"""
        val result = CocAttackPlanFormatter.formatOrFallback(raw)
        assertTrue(result.contains("Collector-focused raid"))
        assertTrue(result.contains("Elixir"))
        assertTrue(result.contains("Barbarian** × 12"))
        assertTrue(result.contains("Deployment phases"))
        assertTrue(result.contains("Splash defense coverage"))
        assertTrue(result.contains("Exact spell inventory"))
    }

    @Test
    fun acceptsFencedJsonAndProviderPreamble() {
        val result = CocAttackPlanFormatter.formatOrFallback(
            "Here is the plan:\n```json\n{\"strategy_summary\":\"خطة تجريبية\",\"phases\":[]}\n```"
        )
        assertTrue(result.contains("خطة تجريبية"))
    }

    @Test
    fun missingQuantitiesRemainMissingAndNoArmyIsNotInvented() {
        val result = CocAttackPlanFormatter.formatOrFallback(
            """{"strategy_summary":"Provisional","army":[{"unit":"Archer","quantity":"","confidence":"low"}]}"""
        )
        assertTrue(result.contains("**Archer**"))
        assertTrue(!result.contains("Archer ×"))
    }

    @Test
    fun malformedOrUnrelatedJsonFallsBackToOriginalText() {
        val malformed = """{"strategy_summary":"""
        assertEquals(malformed, CocAttackPlanFormatter.formatOrFallback(malformed))
        val unrelated = """{"message":"hello"}"""
        assertEquals(unrelated, CocAttackPlanFormatter.formatOrFallback(unrelated))
    }

    @Test
    fun emptyArmyIsClearlyMarked() {
        val result = CocAttackPlanFormatter.formatOrFallback(
            """{"strategy_summary":"Provisional plan","army":[],"phases":[]}"""
        )
        assertTrue(result.contains("No confirmed army composition"))
    }
}
