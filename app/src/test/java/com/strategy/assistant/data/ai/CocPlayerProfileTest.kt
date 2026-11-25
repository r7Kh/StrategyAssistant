package com.strategy.assistant.data.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class CocPlayerProfileTest {

    @Test
    fun profileContextIncludesOnlySuppliedPlayerDetailsAndGoal() {
        val profile = CocPlayerProfile(
            townHallLevel = 17,
            farmingGoal = CocFarmingGoal.DARK_ELIXIR,
            army = "Sneaky Goblins x  sneaky",
            heroesAndEquipment = "Queen available, equipment levels unknown",
            spellsAndSiege = "Jump, Invisibility; no Siege Machine specified"
        )
        val context = profile.toPromptContext()

        assertTrue(context.contains("Town Hall: 17"))
        assertTrue(context.contains("Farming goal: Dark Elixir"))
        assertTrue(context.contains("Sneaky Goblins"))
        assertTrue(context.contains("equipment levels unknown"))
        assertTrue(context.contains("no Siege Machine specified"))
        assertTrue(context.contains("Treat blank fields as unknown"))
        assertFalse(context.contains("Town Hall: 18"))
    }

    @Test
    fun emptyProfileDoesNotInventTownHallOrArmy() {
        val context = CocPlayerProfile().toPromptContext()

        assertFalse(context.contains("Town Hall:"))
        assertFalse(context.contains("Available army"))
        assertTrue(context.contains("Farming goal: Balanced resources"))
    }

    @Test
    fun validationAcceptsKnownTownHallRangeAndNormalizesText() {
        val validated = CocPlayerProfile.validate(
            CocPlayerProfile(townHallLevel = 19, army = "  Miners and Healers  ")
        )

        assertEquals("Miners and Healers", validated.army)
        assertEquals(19, validated.townHallLevel)
    }

    @Test
    fun validationRejectsUnsupportedTownHallAndOversizedFields() {
        assertThrows(IllegalArgumentException::class.java) {
            CocPlayerProfile.validate(CocPlayerProfile(townHallLevel = 20))
        }
        assertThrows(IllegalArgumentException::class.java) {
            CocPlayerProfile.validate(CocPlayerProfile(army = "x".repeat(701)))
        }
    }

    @Test
    fun everyFarmingGoalHasAStableUserFacingLabel() {
        assertEquals(
            setOf("Gold", "Elixir", "Dark Elixir", "Balanced resources"),
            CocFarmingGoal.values().map { it.label }.toSet()
        )
    }
}
