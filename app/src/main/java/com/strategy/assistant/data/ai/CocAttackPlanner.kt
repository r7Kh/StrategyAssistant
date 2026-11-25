package com.strategy.assistant.data.ai

/**
 * Adds a focused planning framework for Clash of Clans farming attacks.
 *
 * This does not calculate battle outcomes or claim to inspect a base image.
 * It guides the AI to request the minimum missing inputs and explain assumptions.
 */
internal object CocAttackPlanner {

    private val attackSignals = listOf(
        "attack", "attacking", "farm", "farming", "loot", "raid", "army", "composition",
        "strategy", "attack plan", "base layout", "war", "هجوم", "اهجم", "مزرعة",
        " farming", "نهب", "تشكيلة", "جيش", "خطة هجوم", "قرية"
    )

    fun isAttackPlanningQuery(query: String): Boolean {
        val normalized = query.lowercase()
        return attackSignals.any { normalized.contains(it) }
    }

    fun planningInstructions(query: String): String {
        if (!isAttackPlanningQuery(query)) return ""
        return buildString {
            appendLine("CLASH OF CLANS FARMING ATTACK PLANNING MODE")
            appendLine("Goal: help the player plan a practical farming attack, prioritizing the resource objective they specify.")
            appendLine("Do not claim to see or analyze a base screenshot unless image input is actually provided to you.")
            appendLine("Do not invent troop levels, housing space, spell capacity, hero equipment effects, current balance values, or guaranteed loot.")
            appendLine("Use the supplied offline game knowledge only for facts it explicitly contains. Treat patch-specific details as dated.")
            appendLine()
            appendLine("INPUTS TO CONSIDER")
            appendLine("- Town Hall level and, when relevant, approximate army/troop levels.")
            appendLine("- Farming target: Gold, Elixir, Dark Elixir, or a balanced mix.")
            appendLine("- Available army composition and housing capacity, plus spells and Siege Machine if relevant.")
            appendLine("- Hero availability and equipment only if it materially changes the plan.")
            appendLine("- Target base screenshot or a short description of exposed collectors, storages, compartments, and defenses.")
            appendLine()
            appendLine("QUESTION POLICY")
            appendLine("- First inspect the current message and recent conversation for details already supplied.")
            appendLine("- If essential information is missing, ask at most two concise questions in one turn. Prioritize Town Hall level and the available army; ask the resource goal only if it is unclear.")
            appendLine("- Do not block all help when some details are missing: give a clearly labeled provisional approach if safe and useful.")
            appendLine()
            appendLine("RESPONSE FORMAT WHEN ENOUGH INFORMATION EXISTS")
            appendLine("1. Recommended army/approach, using only troops the player says are available.")
            appendLine("2. Target selection: what base features and resource placements to look for.")
            appendLine("3. Deployment sequence: entry side, first deployment, support, and ability/spell timing where supported.")
            appendLine("4. Main risks and when to skip the base.")
            appendLine("5. A short alternative if the target layout differs.")
            appendLine()
            appendLine("FARMING PRIORITIES")
            appendLine("- Optimize for the stated resource goal, not automatically for three stars or trophies.")
            appendLine("- Distinguish easy collector raids from storage-focused attacks; explain the trade-off.")
            appendLine("- Consider search cost, army training time, and likely success only qualitatively unless the player supplies reliable values.")
            appendLine("- Never promise a fixed amount of loot or a guaranteed result.")
            appendLine("- Avoid recommending an army that exceeds the player's stated housing/spell capacity.")
            appendLine()
            appendLine("USER REQUEST")
            appendLine(query.trim())
        }
    }
}
