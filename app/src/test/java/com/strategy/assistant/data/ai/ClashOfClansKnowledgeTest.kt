package com.strategy.assistant.data.ai

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ClashOfClansKnowledgeTest {

    @Test
    fun promptIncludesSnapshotVersionAndReviewDate() {
        assertTrue(ClashOfClansKnowledge.PROMPT_CONTEXT.contains(ClashOfClansKnowledge.VERSION))
        assertTrue(ClashOfClansKnowledge.PROMPT_CONTEXT.contains(ClashOfClansKnowledge.VERIFIED_ON))
    }

    @Test
    fun promptIncludesTownHallNineteenFactsAndOfficialSource() {
        val prompt = ClashOfClansKnowledge.PROMPT_CONTEXT
        assertTrue(prompt.contains("Town Hall 19"))
        assertTrue(prompt.contains("Spiky Towers"))
        assertTrue(prompt.contains("Firecracker Towers"))
        assertTrue(prompt.contains("supercell.com/en/games/clashofclans/blog/release-notes/"))
    }

    @Test
    fun promptClearlyStatesThatSnapshotIsNotLiveOrComplete() {
        val prompt = ClashOfClansKnowledge.PROMPT_CONTEXT
        assertTrue(prompt.contains("NOT live game data"))
        assertTrue(prompt.contains("NOT a complete catalog"))
        assertTrue(prompt.contains("Patch-specific facts may have changed"))
    }

    @Test
    fun contextForTownHallQueryReturnsRelevantProgressionFacts() {
        val context = ClashOfClansKnowledge.contextFor("What changed at Town Hall 19?")
        assertTrue(context.contains("Town Hall 19 update"))
        assertTrue(context.contains("Spiky Towers"))
        assertFalse(context.contains("Regular Battles and Ranked Battles"))
    }

    @Test
    fun contextForSquadQueryReturnsSquadRosterAndUnlockRules() {
        val context = ClashOfClansKnowledge.contextFor("Explain the Squad Barracks and squads")
        assertTrue(context.contains("Squads are available from Town Hall 8"))
        assertTrue(context.contains("Golem Witch Squad"))
        assertTrue(context.contains("Squad Level 2"))
    }

    @Test
    fun contextForHeroicSpellQueryReturnsMechanics() {
        val context = ClashOfClansKnowledge.contextFor("How does the Heroic Spell work?")
        assertTrue(context.contains("34%, 36%, 38%, and 40%"))
        assertTrue(context.contains("5-tile radius"))
        assertTrue(context.contains("Town Hall 17"))
    }

    @Test
    fun contextForEquipmentQueryReturnsBalanceFacts() {
        val context = ClashOfClansKnowledge.contextFor("Tell me about Frost Flake")
        assertTrue(context.contains("Frost Flake"))
        assertTrue(context.contains("Royal Champion"))
        assertTrue(context.contains("+18 to +84 DPS"))
    }

    @Test
    fun contextForGuardianQueryReturnsDefensiveOnlyRule() {
        val context = ClashOfClansKnowledge.contextFor("Can I use Smasher in an attack?")
        assertTrue(context.contains("cannot be used in attacks"))
        assertTrue(context.contains("Longshot"))
    }

    @Test
    fun contextForRankedQueryReturnsTownHallRequirements() {
        val context = ClashOfClansKnowledge.contextFor("What is the difference between Ranked and Regular Battles?")
        assertTrue(context.contains("Regular Battles are available from Town Hall 2"))
        assertTrue(context.contains("Ranked Battles are available from Town Hall 7"))
    }
    @Test
    fun supportsArabicAliasesForKnowledgeRetrieval() {
        val context = ClashOfClansKnowledge.contextFor("تعويذة بطولية")
        assertTrue(context.contains("Heroic Spell"))
    }

    @Test
    fun broadQueriesUseBoundedCoreContext() {
        val context = ClashOfClansKnowledge.contextFor("Tell me about Clash of Clans")
        assertTrue(context.contains("Town Hall 19 update"))
        assertFalse(context.contains("Legendary Equipment and Ore economy announcement"))
    }

    @Test
    fun selectedEntriesRetainSourceAndVerificationMetadata() {
        val context = ClashOfClansKnowledge.contextFor("Spiky Tower")
        assertTrue(context.contains("Source: https://supercell.com/"))
        assertTrue(context.contains("Verified: 2026-11-25"))
    }

    @Test
    fun contextForLegendaryEquipmentReturnsOreAndUnlockDetails() {
        val context = ClashOfClansKnowledge.contextFor("What is Legendary Equipment and Blizzard Axe?")
        assertTrue(context.contains("Blizzard Axe"))
        assertTrue(context.contains("79,760 Shiny Ore"))
        assertTrue(context.contains("Town Hall 16"))
        assertTrue(context.contains("Town Hall 18"))
        assertFalse(context.contains("Squads are available from Town Hall 8"))
    }

}
