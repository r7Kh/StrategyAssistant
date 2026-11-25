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
    val keywords: List<String>,
    val facts: List<String>,
    val verifiedOn: String,
    val sourceUrl: String
)

internal object ClashOfClansKnowledge {
    const val VERSION = "2026-11-25"
    const val VERIFIED_ON = "2026-11-25"

    private val entries = listOf(
        CocKnowledgeEntry(
            id = "town-hall-19-launch",
            category = "Progression",
            title = "Town Hall 19 update",
            keywords = listOf("town hall", "townhall", "th19", "spiky tower", "firecracker tower", "guardian shrine", "barbarian", "archer", "wizard", "تاون هول", "مبنى البلدية"),
            facts = listOf(
                "Town Hall 19 was introduced in the Clash of Titans update rolling out on 16 November 2026.",
                "Town Hall 19 allows the Town Hall to be occupied and customized by defensive troops.",
                "The three launch choices are Barbarian, Archer, and Wizard. Barbarian is the default; max-level Archer 15 or Wizard 15 is required to unlock its matching choice.",
                "Defending Town Hall troops deploy at their current upgraded level; Archer and Wizard deploy at level 15 because that is their required unlock level.",
                "Town Hall troop choice is saved per village layout.",
                "New Town Hall 19 defenses/buildings include Spiky Towers, Firecracker Towers, and the Guardian Shrine."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "squads-system",
            category = "Army",
            title = "Squads system and launch roster",
            keywords = listOf("squad", "squads", "squad barracks", "barch", "giant wizard", "wall breaker squad", "minion squad", "balloon squad", "golem witch", "hog rider squad", "miner squad", "healer squad", "pekka titan", "dragon squad", "baby dragon squad", "فرقة", "فرق"),
            facts = listOf(
                "Squads are fixed troop groups deployed together with one tap; Supercell says they provide more troops per housing space than deploying members individually.",
                "Squads are available from Town Hall 8. The Squad Barracks has 12 levels; each level unlocks one squad, with one maximum level added per Town Hall from TH8 through TH19.",
                "Squad members normally use the player's upgraded troop levels. Troops not yet unlocked by the player appear at level 1 inside a squad.",
                "Squad upgrades are exclusive to Town Hall 19. At lower Town Halls, deployed squads are Squad Level 1. Squad abilities unlock at Squad Level 2, and squads cannot be donated.",
                "Launch roster: Barch Squad (TH8: 15 Barbarians + 15 Archers, 15 housing); Giant Wizard Squad (TH9: 3 Giants + 5 Wizards, 25); Wall Breaker Squad (TH10: 5 Wall Breakers, 8); Minion Squad (TH11: 8 Minions, 12).",
                "Launch roster continued: Balloon Squad (TH12: 4 Balloons, 16 housing); Golem Witch Squad (TH13: 1 Golem + 4 Witches, 55); Hog Rider Squad (TH14: 12 Hog Riders, 48); Miner Squad (TH15: 10 Miners, 48).",
                "Launch roster continued: Healer Squad (TH16: 1 Balloon + 5 Healers, 70 housing); P.E.K.K.A Titan Squad (TH17: 1 P.E.K.K.A + 1 Electro Titan, 46); Dragon Squad (TH18: 2 Dragons + 2 Dragon Riders, 85); Baby Dragon Squad (TH19: 3 Baby Dragons, 26).",
                "Squads deploy in squad-specific formations with built-in delays. Their abilities include Splash Resistance, Stealthy Start, Trap Sense, Shielded Start, Focus Attack, and Tantrum."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "town-hall-19-level-catalog",
            category = "Levels",
            title = "Town Hall 19 announced max levels",
            keywords = listOf("max level", "maximum level", "level 19", "level 115", "town hall 19 levels", "th19 levels", "max troop", "مستوى", "ماكس"),
            facts = listOf(
                "The Clash of Titans release notes list these Elixir troop levels: Barbarian 14, Archer 15, Giant 15, Goblin 11, Wall Breaker 15, Balloon 14, Wizard 15, Healer 12, Dragon 14, P.E.K.K.A 14, Baby Dragon 13, Miner 13, Electro Dragon 10, Yeti 9, Dragon Rider 7, Electro Titan 6, Root Rider 5, Thrower 5, Meteor Golem 4.",
                "The same notes list Dark Elixir troop levels: Minion 15, Hog Rider 16, Valkyrie 13, Golem 16, Witch 9, Lava Hound 9, Bowler 11, Headhunter 5, Druid 7, Ruin Witch 5.",
                "The same notes list Super Troop levels: Super Barbarian 10, Super Archer 11, Super Giant 11, Sneaky Goblin 7, Super Wall Breaker 11, Rocket Balloon 10, Super Wizard 11, Super Dragon 12, Inferno Dragon 13, Super Miner 13, Super Yeti 9, Super Minion 12, Super Hog Rider 13, Super Valkyrie 13, Super Witch 9, Ice Hound 9, Super Bowler 11.",
                "The same notes list Hero levels at Hero Hall 13: Barbarian King 115, Archer Queen 115, Minion Prince 100, Grand Warden 90, Royal Champion 60, Dragon Duke 30.",
                "The same notes list Pet Angry Jelly level 15, unlocked from Pet House level 12; the notes say Town Hall 18 players can also access it.",
                "The same notes list Flame Flinger level 6; Healing Spell 13, Lightning Spell 14, Totem Spell 5, Poison Spell 13, Haste Spell 8, and Bat Spell 9.",
                "These are levels explicitly listed in the 16 November 2026 release notes, not a guarantee that every other troop, building, or item has been fully catalogued here."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "town-hall-19-spells-and-heroic-spell",
            category = "Spells",
            title = "Heroic Spell and spell balance notes",
            keywords = listOf("heroic spell", "rage gem", "life gem", "spell factory", "spell", "spells", "heroic", "جنية", "تعويذة", "تعويذات"),
            facts = listOf(
                "The Heroic Spell creates an area where troops take reduced damage, move faster, and ground troops can walk through Walls.",
                "Its four levels reduce troop damage by 34%, 36%, 38%, and 40%; add 5, 6, 7, and 8 movement speed; and last 9.75, 10.75, 11.75, and 12.75 seconds respectively.",
                "The Heroic Spell area has a 5-tile radius. Its effects refresh while troops remain inside and wear off 1 second after they leave.",
                "It affects attacking ground and air troops, Heroes, pets, Clan Castle troops, and summoned units; it does not affect defending troops, Siege Machines, or Totems.",
                "Its speed boost does not stack with Rage or Haste; the fastest boost applies. Damage reduction does not stack with other damage-reduction effects; the strongest applies.",
                "The Heroic Spell unlocks with a new Spell Factory level at Town Hall 17. Its four levels are distributed as two at TH17, one at TH18, and one at TH19.",
                "The Clash of Titans notes say Rage Gem and Life Gem affect up to 260 housing space worth of troops."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "town-hall-19-buildings-defenses",
            category = "Buildings and defenses",
            title = "Town Hall 19 building and defense additions",
            keywords = listOf("spiky tower", "firecracker tower", "guardian shrine", "air defense", "hidden tesla", "x-bow", "multi-archer", "multi-gear", "ricochet cannon", "builder hut", "clan castle", "defense", "building", "دفاع", "مبنى"),
            facts = listOf(
                "The Town Hall 19 update adds a fifth Air Defense, a tenth Spring Trap, 25 additional Walls (350 total), two Spiky Towers, two Firecracker Towers, and one Guardian Shrine.",
                "The release notes list Town Hall 19 building levels: Town Hall 19, Clan Castle 15 (with +5 troop capacity, now 60), Laboratory 17, Hero Hall 13, Gold Storage 20, Elixir Storage 20, and Dark Elixir Storage 14.",
                "The release notes list resource building levels from Town Hall 18: Gold Mine 18, Elixir Collector 18, and Dark Elixir Drill 12.",
                "The release notes list defense levels: Air Defense 17, Hidden Tesla 18, X-Bow 14, Multi-Archer Tower 5, Multi-Gear Tower 4, Ricochet Cannon 5, and Builder's Hut 9.",
                "Firecracker Tower unlocks at Town Hall 19, has a 10-tile range, two levels, and a maximum count of two, according to the update notes.",
                "The Guardian Shrine is free and instant to place at Town Hall 19. It is where Guardians are upgraded and the defending Guardian is selected; that selection is now shared across layouts."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "town-hall-18-guardians",
            category = "Heroes and defense",
            title = "Town Hall 18 Guardians",
            keywords = listOf("guardian", "guardians", "smasher", "longshot", "guardian shrine", "town hall 18", "th18", "حارس", "الحراس"),
            facts = listOf(
                "Guardians are a Town Hall 18 defensive feature and cannot be used in attacks. Only one Guardian defends at a time.",
                "Upgrading a Guardian requires a free Builder; a Guardian cannot be selected for active defense while it is upgrading.",
                "Town Hall 18 unlocks Smasher and Longshot. Smasher is melee-based and damages ground and air enemies with splash damage; it rages when the Town Hall is destroyed and leaves rage behind when defeated.",
                "Longshot is ranged and damages ground and air enemies with splash damage; it explodes when defeated and damages nearby enemies.",
                "At Town Hall 19, the Guardian Shrine replaces the Town Hall as the place to choose and upgrade the defending Guardian."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/town-hall-18-crash-lands-update/"
        ),
        CocKnowledgeEntry(
            id = "town-hall-18-new-units-and-defenses",
            category = "Army and defenses",
            title = "Town Hall 18 additions",
            keywords = listOf("meteor golem", "meteormite", "totem spell", "revenge tower", "super wizard tower", "light beam", "hero bell", "bomb hive", "th18", "تاون هول 18"),
            facts = listOf(
                "Meteor Golem became a permanent troop after first appearing as a temporary event troop; it unlocks at Town Hall 17 and throws Meteormites over Walls. Meteormites can remerge into a Meteor Golem.",
                "Totem Spell unlocks at Town Hall 16. It stuns and damages buildings in its landing radius and can temporarily be targeted by ground and air enemies.",
                "Revenge Tower unlocks at Town Hall 18 and increases its power and hitpoints as 5, 20, and 40 buildings are destroyed during a defense.",
                "At Town Hall 18, two Wizard Towers can be merged into a Super Wizard Tower whose attacks can chain to up to 15 nearby enemies.",
                "Crafted Defense Phase 2 introduced Light Beam, Hero Bell, and Bomb Hive. The official notes described that phase as scheduled to last four months from the November 2025 update; do not assume that phase is still active."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/town-hall-18-crash-lands-update/"
        ),
        CocKnowledgeEntry(
            id = "sound-of-clash-april-2026",
            category = "Army, equipment and progression",
            title = "The Sound of Clash update",
            keywords = listOf("sky wagon", "electro fangs", "dragon duke", "legend league", "april 2026", "sound of clash", "golem 15", "yeti 8", "root rider 4", "balloon 13", "تنين", "عربة"),
            facts = listOf(
                "The April 2026 update introduced the Sky Wagon, an aerial Siege Machine that can move independently and releases barrels containing Clan Castle troops alongside Minions, a Balloon, and a Baby Dragon.",
                "At maximum level, each Sky Wagon deployment releases 5 level-14 Minions, 1 level-13 Balloon, and 1 level-12 Baby Dragon, according to the update notes.",
                "The update introduced Dragon Duke Common Equipment Electro Fangs. It is passive, chains across up to four targets, each jump deals 20% less damage, and each strike can deal up to 400 damage.",
                "Electro Fangs unlock at Town Hall 17 with Dragon Duke.",
                "The April 2026 notes added troop levels including Barbarian 13, Goblin 10, Valkyrie 12, Golem 15, Dragon 13, Balloon 13, Yeti 8, Headhunter 4, Root Rider 4, and Druid 6.",
                "The same notes added spell levels Rage 7, Freeze 8, Clone 9, Recall 7, Overgrowth 5, and Ice Block 6; Siege Machine levels Battle Drill 6, Siege Barracks 6, Log Launcher 6, and Battle Blimp 6.",
                "Legend League was split into Legend III, Legend II, and Legend I with different tournament and battle-count structures. These details are patch-specific and may change in later updates."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-sound-of-clash-update/"
        ),
        CocKnowledgeEntry(
            id = "ranked-and-regular-battles",
            category = "Battles and matchmaking",
            title = "Regular Battles and Ranked Battles",
            keywords = listOf("ranked", "regular battle", "regular battles", "magic shield", "legend shield", "revenge", "tournament mode", "spring trap", "league", "ترتيب", "هجوم عادي", "درع"),
            facts = listOf(
                "The October 2025 update split multiplayer into Regular Battles and Ranked Battles.",
                "Regular Battles are available from Town Hall 2 and support farming, events, and practice without risking trophies.",
                "Ranked Battles are available from Town Hall 7 and use weekly tournaments, leagues, Town Hall-range matchmaking, and a limited number of attacks.",
                "The October 2025 notes describe an 8-hour Magic Shield after a village is attacked; attacking does not reduce its duration, and Magic Shields apply only to Regular Battles.",
                "Revenge attacks are available in Regular Battles for 12 hours after a significant resource raid. The target is a Shadow Base version of the opponent's village.",
                "The October 2025 update renamed Hard Mode to Tournament Mode and documented specific combat modifiers. Do not assume those modifiers remain unchanged without a newer source."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/get-ready-for-ranked-update/"
        ),
        CocKnowledgeEntry(
            id = "town-hall-19-balance-notes",
            category = "Balance",
            title = "Town Hall 19 launch balance notes",
            keywords = listOf("rage gem", "life gem", "frost flake", "royal champion", "yeti", "super yeti", "golem", "balance", "housing space", "جوهرة الغضب", "جوهرة الحياة"),
            facts = listOf(
                "The Clash of Titans release notes state that Rage Gem and Life Gem affect up to 260 housing space worth of troops.",
                "The release notes describe cost and time reductions for Town Hall 16–18 content, alongside many level adjustments. Do not infer a specific item's new cost or level unless the exact item is documented.",
                "Frost Flake gains Royal Champion damage across equipment levels 1–27, ranging from +18 to +84 DPS according to the release notes.",
                "The release notes adjust the first Yetimite spawn thresholds for Yeti and Super Yeti and increase Golem health and damage at specified levels. Do not infer unlisted level-specific numbers."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/the-clash-of-titans-update-is-rolling-out/"
        ),
        CocKnowledgeEntry(
            id = "august-2026-update",
            category = "Recent history",
            title = "August 2026 update",
            keywords = listOf("august", "diggy", "pet", "supercharge", "builder's hut", "monolith", "أغسطس", "ديجي"),
            facts = listOf(
                "The official August 2026 release notes announced Diggy pet levels 11–15 for Town Hall 18.",
                "The same update announced new Supercharge levels for the Builder's Hut and Monolith.",
                "These are August 2026 notes and must not be described as the newest update after the November 2026 Clash of Titans release."
            ),
            verifiedOn = "2026-11-25",
            sourceUrl = "https://supercell.com/en/games/clashofclans/blog/release-notes/august-update-3/"
        )
    )

    fun contextFor(query: String): String {
        val normalizedQuery = query.lowercase()
        val matchingEntries = entries.filter { entry ->
            entry.keywords.any { keyword -> normalizedQuery.contains(keyword.lowercase()) }
        }
        val selectedEntries = matchingEntries.ifEmpty { entries }
        return buildContext(selectedEntries)
    }

    private fun buildContext(selectedEntries: List<CocKnowledgeEntry>): String = buildString {
        appendLine("CLASH OF CLANS OFFLINE KNOWLEDGE SNAPSHOT")
        appendLine("Snapshot version: $VERSION")
        appendLine("Last source review: $VERIFIED_ON")
        appendLine("This is a curated offline snapshot, NOT live game data and NOT a complete catalog.")
        appendLine("Use only facts explicitly listed below as verified game facts. Do not extrapolate exact levels, costs, statistics, unlock requirements, or current meta.")
        appendLine("If a requested fact is absent, say it is not in the installed knowledge snapshot. Do not invent it.")
        appendLine("Patch-specific facts may have changed after their cited update; clearly identify the update date when relevant.")
        appendLine("For attack advice, ask for the user's Town Hall, army, hero/equipment levels, and target/base layout only when those details materially affect the recommendation.")
        appendLine()
        selectedEntries.forEach { entry ->
            appendLine("[${entry.category}] ${entry.title}")
            entry.facts.forEach { appendLine("- $it") }
            appendLine("Source: ${entry.sourceUrl}")
            appendLine("Verified: ${entry.verifiedOn}")
            appendLine()
        }
    }

    /**
     * Full snapshot retained for validation and tests. Production requests use contextFor()
     * to retrieve relevant records when a query contains known keywords.
     */
    val PROMPT_CONTEXT: String = buildContext(entries)
}
