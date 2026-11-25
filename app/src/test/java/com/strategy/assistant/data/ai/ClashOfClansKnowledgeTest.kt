package com.strategy.assistant.data.ai

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
        assertTrue(prompt.contains("Spiky Tower"))
        assertTrue(prompt.contains("Firecracker Tower"))
        assertTrue(prompt.contains("supercell.com/en/games/clashofclans/blog/release-notes/"))
    }

    @Test
    fun promptClearlyStatesThatSnapshotIsNotLiveOrComplete() {
        val prompt = ClashOfClansKnowledge.PROMPT_CONTEXT
        assertTrue(prompt.contains("NOT live game data"))
        assertTrue(prompt.contains("NOT a complete catalog"))
    }
    @Test
    fun contextForTownHallQueryReturnsRelevantProgressionFacts() {
        val context = ClashOfClansKnowledge.contextFor("What changed at Town Hall 19?")
        assertTrue(context.contains("Town Hall 19 update"))
        assertTrue(context.contains("Spiky Tower"))
    }

    @Test
    fun contextForEquipmentQueryReturnsBalanceFacts() {
        val context = ClashOfClansKnowledge.contextFor("Tell me about Frost Flake")
        assertTrue(context.contains("Frost Flake"))
        assertTrue(context.contains("Royal Champion"))
    }

}
