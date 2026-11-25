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


    fun attackPlanInstructions(query: String): String {
        if (!isAttackPlanningQuery(query)) return ""
        return buildString {
            appendLine("STRUCTURED CLASH OF CLANS ATTACK PLAN OUTPUT")
            appendLine("Return one valid JSON object only. Do not use Markdown fences or prose outside JSON.")
            appendLine("Use exactly this schema:")
            appendLine("""{"title":"string","strategy_summary":"string","resource_goal":"Gold|Elixir|Dark Elixir|Balanced|Unknown","target_criteria":["string"],"base_observations":[{"item":"string","location":"string","confidence":"high|medium|low","evidence":"string"}],"army":[{"unit":"string","quantity":"string or empty if unknown","confidence":"high|medium|low","reason":"string"}],"entry":{"side":"string or Unknown","rationale":"string","confidence":"high|medium|low"},"phases":[{"name":"string","actions":["string"],"timing":"string"}],"spell_timing":["string"],"risks":["string"],"skip_if":["string"],"fallback":"string","assumptions":["string"],"evaluation":{"resource_fit":"strong|moderate|weak|unknown","army_fit":"strong|moderate|weak|unknown","entry_safety":"strong|moderate|weak|unknown","training_efficiency":"strong|moderate|weak|unknown","main_tradeoff":"string","recommendation_reason":"string"},"alternatives":[{"name":"string","best_when":"string","tradeoff":"string"}],"missing_info":["string"]}""")
            appendLine("Never invent quantities, levels, housing space, spell capacity, loot amounts, or percentages. If unknown, use an empty quantity and explicitly list the missing detail.")
            appendLine("Use only units confirmed in the current conversation/profile. If none are known, leave army empty and give target-selection guidance instead.")
            appendLine("For screenshot observations, include only visible evidence. Do not infer hidden traps, exact levels, exact coordinates, or guaranteed pathing.")
            appendLine("Evaluate resource-goal fit, confirmed army fit, entry safety from visible evidence, and training efficiency only when inputs support it. Use strong/moderate/weak/unknown labels, never numeric scores.")
            appendLine("Explain the main trade-off and why the recommendation fits the player. Offer at most two meaningfully different alternatives only when supported by supplied information; state when each is preferable and its trade-off.")
            appendLine("Do not rank alternatives using invented metrics or claim a measured win rate. If evidence is insufficient, mark evaluation unknown and explain what is missing.")
            appendLine("Use qualitative confidence only. Do not express success probability as a number or guarantee stars/loot.")
            appendLine("Phases should be practical and ordered (funnel, main force, support/heroes, spells, cleanup) only where relevant to the confirmed army.")
            appendLine("Ask no more than two essential follow-up questions, represented in missing_info. Avoid repeating details already supplied.")
            appendLine("If inputs are incomplete, return a clearly provisional safe plan and state assumptions.")
            appendLine()
            appendLine("PLAYER REQUEST")
            appendLine(query.trim())
        }
    }

    fun screenshotAnalysisInstructions(): String = buildString {
        appendLine("CLASH OF CLANS VILLAGE SCREENSHOT ANALYSIS")
        appendLine("An image is attached to this request. Inspect only what is actually visible in the supplied screenshot.")
        appendLine("First identify the screenshot context: village/base view, attack replay, scouting view, or unclear. If it is not an enemy base view, say so and do not invent a base layout.")
        appendLine("Describe observations in three confidence groups: clearly visible, probable but uncertain, and not identifiable from this image.")
        appendLine("Inspect visible resource collectors and storages, their apparent placement/accessibility, outer buildings, walls/compartments, defensive coverage, and plausible approach lanes.")
        appendLine("Do not infer exact loot amounts, building levels, hidden traps, troop levels, or upgrade status from appearance unless clearly readable.")
        appendLine("Do not claim pixel-perfect object detection, exact coordinates, or guaranteed pathing. Use relative screen directions such as top, bottom, left, and right.")
        appendLine("Recommend an entry side only when the visible layout supports it. Explain the visual reason and identify key risks such as splash damage, air defenses, single-target defenses, compartments, or funnel gaps only when visible.")
        appendLine("If the screenshot is cropped, blurry, zoomed out, obstructed, or missing important edges, state what additional screenshot or detail is needed.")
        appendLine("For a farming plan, prioritize the user's stated resource objective. Separate collector access from storage access, and give a cautious deployment sequence using only troops the player confirms they have.")
        appendLine("Never present an uncertain visual guess as a fact. If the model cannot distinguish a building, say 'unclear' rather than naming it.")
        appendLine("Return valid JSON only, with no Markdown fences or text outside JSON.")
        appendLine("Use this top-level shape: {\"screenshot_context\":string,\"observations\":[{\"category\":string,\"name\":string,\"relative_location\":string,\"confidence\":\"high|medium|low\",\"visual_evidence\":string}],\"resource_targets\":[string],\"recommended_entry\":{\"side\":string,\"reason\":string,\"confidence\":\"high|medium|low\"},\"attack_sequence\":[string],\"risks\":[string],\"limitations\":[string],\"next_question\":string}.")
        appendLine("Every observation must be grounded in visible evidence. Use an empty array when there are no reliable observations. Use low confidence or omit an observation rather than inventing a building.")
        appendLine("Confidence is a qualitative visual estimate, not a measured probability. Do not include numeric percentages.")
        appendLine("Keep the analysis concise and finish with the single most useful missing player detail if required.")
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
            appendLine("PLAYER PROFILE AND INPUTS")
            appendLine("- Use the saved player profile when it is supplied: Town Hall, farming goal, army, heroes/equipment, spells/Siege Machine, and notes.")
            appendLine("- Treat every profile field as user-provided and potentially outdated. Prefer the latest message if it conflicts with the profile; blank fields mean unknown.")
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
