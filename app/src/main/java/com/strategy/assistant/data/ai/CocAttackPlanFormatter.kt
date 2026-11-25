package com.strategy.assistant.data.ai

import org.json.JSONArray
import org.json.JSONObject

/** Formats a structured CoC attack plan without inventing missing values. */
internal object CocAttackPlanFormatter {

    fun formatOrFallback(raw: String): String {
        val jsonText = extractJsonObject(raw) ?: return raw
        val plan = runCatching { JSONObject(jsonText) }.getOrNull() ?: return raw
        if (!plan.has("strategy_summary") && !plan.has("phases") && !plan.has("army")) return raw

        return buildString {
            appendLine("## ⚔ ATTACK PLAN")
            appendLine("**${plan.safeString("title").ifBlank { "Clash of Clans attack plan" }}**")
            plan.safeString("strategy_summary").takeIf(String::isNotBlank)?.let {
                appendLine()
                appendLine(it)
            }
            plan.safeString("resource_goal").takeIf(String::isNotBlank)?.let {
                appendLine()
                appendLine("**Farming objective:** $it")
            }
            appendArray(plan.optJSONArray("target_criteria"), "Target selection")
            appendLine()
            appendLine("### Base observations")
            val observations = plan.optJSONArray("base_observations") ?: JSONArray()
            val visible = (0 until observations.length()).mapNotNull { observations.optJSONObject(it) }
            if (visible.isEmpty()) appendLine("- No reliable base observations supplied.")
            visible.forEach { item ->
                val name = item.safeString("item").ifBlank { "Unidentified feature" }
                val location = item.safeString("location").ifBlank { "Location unclear" }
                appendLine("- **$name** — $location · ${confidence(item.safeString("confidence"))} confidence")
                item.safeString("evidence").takeIf(String::isNotBlank)?.let { appendLine("  - Evidence: $it") }
            }
            appendLine()
            appendLine("### Army")
            val army = plan.optJSONArray("army") ?: JSONArray()
            val units = (0 until army.length()).mapNotNull { army.optJSONObject(it) }
            if (units.isEmpty()) appendLine("- No confirmed army composition. Do not train an assumed army from this plan.")
            units.forEach { unit ->
                val name = unit.safeString("unit").ifBlank { "Unspecified unit" }
                val quantity = unit.safeString("quantity")
                appendLine("- **$name**${if (quantity.isBlank()) "" else " × $quantity"} · ${confidence(unit.safeString("confidence"))} confidence")
                unit.safeString("reason").takeIf(String::isNotBlank)?.let { appendLine("  - $it") }
            }
            plan.optJSONObject("entry")?.let { entry ->
                appendLine()
                appendLine("### Entry")
                appendLine("**${entry.safeString("side").ifBlank { "Not determined" }}** · ${confidence(entry.safeString("confidence"))} confidence")
                entry.safeString("rationale").takeIf(String::isNotBlank)?.let { appendLine(it) }
            }
            val phases = plan.optJSONArray("phases") ?: JSONArray()
            if (phases.length() > 0) {
                appendLine()
                appendLine("### Deployment phases")
                for (index in 0 until phases.length()) {
                    val phase = phases.optJSONObject(index) ?: continue
                    appendLine("${index + 1}. **${phase.safeString("name").ifBlank { "Phase" }}**")
                    phase.safeStringArray("actions").forEach { appendLine("   - $it") }
                    phase.safeString("timing").takeIf(String::isNotBlank)?.let { appendLine("   - Timing: $it") }
                }
            }
            appendArray(plan.optJSONArray("spell_timing"), "Spell timing")
            appendArray(plan.optJSONArray("risks"), "Main risks")
            appendArray(plan.optJSONArray("skip_if"), "When to skip this base")
            plan.safeString("fallback").takeIf(String::isNotBlank)?.let {
                appendLine()
                appendLine("### Alternative")
                appendLine(it)
            }
            plan.optJSONObject("evaluation")?.let { evaluation ->
                appendLine()
                appendLine("### Strategy evaluation")
                appendLine("- Resource-goal fit: ${evaluation.safeString("resource_fit").ifBlank { "unknown" }}")
                appendLine("- Army fit: ${evaluation.safeString("army_fit").ifBlank { "unknown" }}")
                appendLine("- Entry safety: ${evaluation.safeString("entry_safety").ifBlank { "unknown" }}")
                appendLine("- Training efficiency: ${evaluation.safeString("training_efficiency").ifBlank { "unknown" }}")
                evaluation.safeString("main_tradeoff").takeIf(String::isNotBlank)?.let { appendLine("- Main trade-off: $it") }
                evaluation.safeString("recommendation_reason").takeIf(String::isNotBlank)?.let { appendLine("- Why this plan: $it") }
            }
            val alternatives = plan.optJSONArray("alternatives") ?: JSONArray()
            if (alternatives.length() > 0) {
                appendLine()
                appendLine("### Alternative approaches")
                for (index in 0 until minOf(alternatives.length(), 2)) {
                    val alternative = alternatives.optJSONObject(index) ?: continue
                    appendLine("- **${alternative.safeString("name").ifBlank { "Alternative ${index + 1}" }}**")
                    alternative.safeString("best_when").takeIf(String::isNotBlank)?.let { appendLine("  - Best when: $it") }
                    alternative.safeString("tradeoff").takeIf(String::isNotBlank)?.let { appendLine("  - Trade-off: $it") }
                }
            }
            appendArray(plan.optJSONArray("assumptions"), "Assumptions")
            appendArray(plan.optJSONArray("missing_info"), "Details needed")
            appendLine()
            val validationWarnings = CocAttackPlanValidator.validate(raw)
            if (validationWarnings.isNotEmpty()) {
                appendLine()
                appendLine("### Plan validation warnings")
                validationWarnings.forEach { appendLine("- $it") }
            }
            appendLine()
            appendLine("_Provisional strategy guidance only. Verify the army, capacity, target layout, and current in-game conditions before attacking._")
        }.trim()
    }

    private fun extractJsonObject(raw: String): String? {
        val text = raw.trim().removePrefix("```json").removePrefix("```JSON")
            .removePrefix("```").removeSuffix("```").trim()
        val start = text.indexOf('{')
        if (start < 0) return null
        var depth = 0
        var inString = false
        var escaped = false
        for (index in start until text.length) {
            val char = text[index]
            if (inString) {
                if (escaped) escaped = false
                else if (char == '\\') escaped = true
                else if (char == '"') inString = false
            } else when (char) {
                '"' -> inString = true
                '{' -> depth++
                '}' -> {
                    depth--
                    if (depth == 0) return text.substring(start, index + 1)
                }
            }
        }
        return null
    }

    private fun JSONObject.safeString(key: String): String =
        if (!has(key) || isNull(key)) "" else opt(key)?.toString()?.trim().orEmpty().takeUnless { it == "null" }.orEmpty()

    private fun JSONObject.safeStringArray(key: String): List<String> {
        val array = optJSONArray(key) ?: return emptyList()
        return (0 until array.length()).mapNotNull { index ->
            if (array.isNull(index)) null else array.optString(index).trim().takeIf { it.isNotBlank() && it != "null" }
        }
    }

    private fun StringBuilder.appendArray(array: JSONArray?, heading: String) {
        if (array == null) return
        val values = (0 until array.length()).mapNotNull { index ->
            if (array.isNull(index)) null else array.optString(index).trim().takeIf { it.isNotBlank() && it != "null" }
        }
        if (values.isEmpty()) return
        appendLine()
        appendLine("### $heading")
        values.forEach { appendLine("- $it") }
    }

    private fun confidence(value: String): String = when (value.lowercase()) {
        "high" -> "High"
        "medium" -> "Medium"
        "low" -> "Low"
        else -> "Unclear"
    }
}
