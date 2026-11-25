package com.strategy.assistant.data.ai

/**
 * Curated, source-backed Clash of Clans facts.
 *
 * This is an offline snapshot, not a live game database. Update the entries and
 * verification date when official release notes or game data are reviewed.
 */
internal data class CocKnowledgeEntry(
    val id: String,
    val category: String,
    val title: String,
    val facts: List<String>,
    val verifiedOn: String,
    val sourceUrl: String
)

internal object ClashOfClansKnowledge {
    const val VERSION = "2026-11-16"
    const val VERIFIED_ON = "2026-11-25"

    private val entries = listOf(
        CocKnowledgeEntry(
            id = "town-hall-19-launch",
            category = "Progression",
            title = "Town Hall 19 update",
            facts = listOf(
                "Town Hall 19 was introduced in the Clash of Titans update rolling out on 16 November 2026.",
                "Town Hall 19 allows the Town Hall to be occupied and customized by defensive troops.",
                "At launch, the announced Town Hall troop choices are Barbarian, Archer, and Wizard; Barbarian is the default choice.",
                "The update also introduced the Spiky Tower and Firecracker Tower as new defenses.",
                "The update introduced Squads: deployable troop hordes unlocked at Town Hall 8, with their unique abilities upgraded at Town Hall 19."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "town-hall-19-balance-notes",
            category = "Balance",
            title = "Town Hall 19 launch balance notes",
            facts = listOf(
                "The Clash of Titans release notes state that Rage Gem and Life Gem affect up to 260 housing space worth of troops.",
                "The release notes describe cost and time reductions for Town Hall 16–18 content, alongside many level adjustments. Do not infer a specific item's new cost or level unless the exact item is documented.",
                "The release notes state that Frost Flake gains Royal Champion damage across equipment levels 1–27.",
                "The release notes include changes to Yeti and Super Yeti Yetimite spawn thresholds and to Golem health and damage. Ask for the exact unit and level before giving numeric details not listed here."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "august-2026-update",
            category = "Recent history",
            title = "August 2026 update",
            facts = listOf(
                "The official August 2026 release notes announced Diggy pet levels 11–15 for Town Hall 18.",
                "The same update announced new Supercharge levels for the Builder's Hut and Monolith.",
                "These are August 2026 notes and must not be described as the newest update after the November 2026 Clash of Titans release."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/august-update-3/"
        )
    )

    /**
     * Bounded context keeps prompt size predictable. Facts are authored from official
     * Supercell release notes and are intentionally conservative.
     */
    val PROMPT_CONTEXT: String = buildString {
        appendLine("CLASH OF CLANS OFFLINE KNOWLEDGE SNAPSHOT")
        appendLine("Snapshot version: $VERSION")
        appendLine("Last source review: $VERIFIED_ON")
        appendLine("This is a curated offline snapshot, NOT live game data and NOT a complete catalog.")
        appendLine("Use only facts explicitly listed below as verified game facts. Do not extrapolate exact levels, costs, statistics, unlock requirements, or current meta.")
        appendLine("If a requested fact is absent, say it is not in the installed knowledge snapshot. Do not invent it.")
        appendLine("For attack advice, ask for the user's Town Hall, army, hero/equipment levels, and target/base layout only when those details materially affect the recommendation.")
        appendLine()
        entries.forEach { entry ->
            appendLine("[${entry.category}] ${entry.title}")
            entry.facts.forEach { appendLine("- $it") }
            appendLine("Source: ${entry.sourceUrl}")
            appendLine("Verified: ${entry.verifiedOn}")
            appendLine()
        }
    }
}
