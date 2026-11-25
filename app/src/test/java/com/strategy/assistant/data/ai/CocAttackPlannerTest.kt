package com.strategy.assistant.data.ai

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CocAttackPlannerTest {

    @Test
    fun detectsFarmingAndLootPlanningRequests() {
        assertTrue(CocAttackPlanner.isAttackPlanningQuery("Help me plan a farming attack for Dark Elixir"))
        assertTrue(CocAttackPlanner.isAttackPlanningQuery("شو أفضل تشكيلة هجوم للفارم؟"))
    }

    @Test
    fun ignoresUnrelatedGameQuestions() {
        assertFalse(CocAttackPlanner.isAttackPlanningQuery("What does Town Hall 19 unlock?"))
        assertFalse(CocAttackPlanner.isAttackPlanningQuery("Explain the Heroic Spell"))
    }

    @Test
    fun instructionsRequirePlayerSpecificInputsAndAvoidGuarantees() {
        val instructions = CocAttackPlanner.planningInstructions("Plan a farming attack")
        assertTrue(instructions.contains("Town Hall level"))
        assertTrue(instructions.contains("available army"))
        assertTrue(instructions.contains("Never promise a fixed amount of loot"))
        assertTrue(instructions.contains("ask at most two concise questions"))
    }

    @Test
    fun structuredPlanInstructionsRequireEvidenceAndSafeUnknowns() {
        val instructions = CocAttackPlanner.attackPlanInstructions("Plan a farming attack")
        assertTrue(instructions.contains("valid JSON object only"))
        assertTrue(instructions.contains("strategy_summary"))
        assertTrue(instructions.contains("Never invent quantities"))
        assertTrue(instructions.contains("qualitative confidence"))
        assertTrue(instructions.contains("no more than two essential follow-up questions"))
        assertTrue(instructions.contains("never numeric scores"))
        assertTrue(instructions.contains("at most two meaningfully different alternatives"))
        assertTrue(instructions.contains("measured win rate"))
    }

    @Test
    fun unrelatedQuestionsReceiveNoStructuredPlanPrompt() {
        assertTrue(CocAttackPlanner.attackPlanInstructions("What is a Guardian?").isBlank())
    }

    @Test
    fun unrelatedQuestionsReceiveNoAttackPlanningPrompt() {
        assertTrue(CocAttackPlanner.planningInstructions("What is a Guardian?").isBlank())
    }

    @Test
    fun detectsPostAttackReviewWithoutTriggeringOnGeneralAdvice() {
        assertTrue(CocAttackPlanner.isPostAttackReviewQuery("هاي نتيجة الهجوم جبت 500 ألف ذهب"))
        assertTrue(CocAttackPlanner.isPostAttackReviewQuery("After my attack I gained loot"))
        assertFalse(CocAttackPlanner.isPostAttackReviewQuery("Plan my next farming attack"))
    }

    @Test
    fun postAttackReviewRequiresEvidenceAndDoesNotClaimPersistentLearning() {
        val instructions = CocAttackPlanner.postAttackReviewInstructions("نتيجة الهجوم")
        assertTrue(instructions.contains("only when the user supplies its result or replay evidence"))
        assertTrue(instructions.contains("Never invent loot"))
        assertTrue(instructions.contains("only save it if the user explicitly asks"))
    }

    @Test
    fun screenshotInstructionsSeparateVisibleAndUncertainObservations() {
        val instructions = CocAttackPlanner.screenshotAnalysisInstructions()
        assertTrue(instructions.contains("clearly visible"))
        assertTrue(instructions.contains("probable but uncertain"))
        assertTrue(instructions.contains("not identifiable"))
        assertTrue(instructions.contains("Do not infer exact loot amounts"))
        assertTrue(instructions.contains("relative screen directions"))
    }

    @Test
    fun screenshotInstructionsWarnAgainstInventingBaseLayout() {
        val instructions = CocAttackPlanner.screenshotAnalysisInstructions()
        assertTrue(instructions.contains("do not invent a base layout"))
        assertTrue(instructions.contains("say 'unclear'"))
        assertTrue(instructions.contains("single most useful missing player detail"))
    }

}
