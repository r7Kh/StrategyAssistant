package com.strategy.assistant.data.ai

import org.json.JSONObject

/**
 * Deterministic sanity checks for model-produced attack plans.
 * This validator flags suspicious or incomplete fields; it never invents or repairs game data.
 */
internal object CocAttackPlanValidator {
    private val confidenceValues = setOf("high", "medium", "low")
    private val evaluationValues = setOf("strong", "moderate", "weak", "unknown")

    fun validate(raw: String): List<String> {
        val jsonText = extractObject(raw) ?: return listOf("The response was not a complete structured plan.")
        val plan = runCatching { JSONObject(jsonText) }.getOrNull()
            ?: return listOf("The structured plan could not be parsed.")
        val warnings = mutableListOf<String>()
        val army = plan.optJSONArray("army")
        val seenUnits = mutableSetOf<String>()
        if (army == null || army.length() == 0) {
            warnings += "No army composition was confirmed; do not train an assumed army."
        } else {
            for (i in 0 until army.length()) {
                val unit = army.optJSONObject(i) ?: continue
                val name = unit.optString("unit").trim()
                if (name.isBlank()) warnings += "An army entry has no unit name."
                if (name.isNotBlank() && !seenUnits.add(name.lowercase())) {
                    warnings += "Duplicate army entry: $name. Confirm whether quantities should be combined."
                }
                val quantity = unit.optString("quantity").trim()
                if (quantity.isNotBlank() && !quantity.matches(Regex("\\d+"))) {
                    warnings += "Quantity for ${name.ifBlank { "an army unit" }} is not a simple whole number; verify it."
                }
                val confidence = unit.optString("confidence").lowercase()
                if (confidence.isNotBlank() && confidence !in confidenceValues) {
                    warnings += "Confidence for ${name.ifBlank { "an army unit" }} is not a supported qualitative label."
                }
            }
        }
        val entryConfidence = plan.optJSONObject("entry")?.optString("confidence")?.lowercase().orEmpty()
        if (entryConfidence.isNotBlank() && entryConfidence !in confidenceValues) {
            warnings += "Entry confidence is not a supported qualitative label."
        }
        val evaluation = plan.optJSONObject("evaluation")
        if (evaluation != null) {
            listOf("resource_fit", "army_fit", "entry_safety", "training_efficiency").forEach { key ->
                val value = evaluation.optString(key).lowercase()
                if (value.isNotBlank() && value !in evaluationValues) {
                    warnings += "Evaluation field $key uses an unsupported label; treat it as unknown."
                }
            }
        }
        if ((plan.optJSONArray("alternatives")?.length() ?: 0) > 2) {
            warnings += "More than two alternatives were returned; only the first two should be considered."
        }
        if ((plan.optJSONArray("phases")?.length() ?: 0) > 8) {
            warnings += "The plan contains unusually many deployment phases; simplify and verify the sequence."
        }
        return warnings.distinct()
    }

    private fun extractObject(raw: String): String? {
        val start = raw.indexOf('{')
        if (start < 0) return null
        var depth = 0
        var inString = false
        var escaped = false
        for (i in start until raw.length) {
            val c = raw[i]
            if (inString) {
                if (escaped) escaped = false
                else if (c == '\\\\') escaped = true
                else if (c == '"') inString = false
            } else when (c) {
                '"' -> inString = true
                '{' -> depth++
                '}' -> if (--depth == 0) return raw.substring(start, i + 1)
            }
        }
        return null
    }
}
