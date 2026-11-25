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
        assertTrue(prompt.contains("developer.clashofclans.com").not())
        assertTrue(prompt.contains("supercell.com/en/games/clashofclans/blog/release-notes/"))
    }

    @Test
    fun promptClearlyStatesThatSnapshotIsNotLiveOrComplete() {
        val prompt = ClashOfClansKnowledge.PROMPT_CONTEXT
        assertTrue(prompt.contains("NOT live game data"))
        assertTrue(prompt.contains("NOT a complete catalog"))
    }
}
