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
    fun unrelatedQuestionsReceiveNoAttackPlanningPrompt() {
        assertTrue(CocAttackPlanner.planningInstructions("What is a Guardian?").isBlank())
    }
}
