package com.strategy.assistant.data.ai

import org.json.JSONArray
import org.json.JSONObject

/** Converts model screenshot JSON into readable text; confidence is not a calibrated probability. */
internal object CocScreenshotReportFormatter {
    fun formatOrFallback(raw: String): String {
        val jsonText = raw.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
        val report = runCatching { JSONObject(jsonText) }.getOrNull() ?: return raw
        if (!report.has("observations") && !report.has("screenshot_context")) return raw
        return buildString {
            appendLine("## Village screenshot report")
            report.optString("screenshot_context").takeIf { it.isNotBlank() }?.let { appendLine(); appendLine("**Screenshot type:** $it") }
            val observations = report.optJSONArray("observations") ?: JSONArray()
            appendLine(); appendLine("### Visual observations")
            if (observations.length() == 0) appendLine("No reliable building observations could be identified.")
            for (index in 0 until observations.length()) {
                val item = observations.optJSONObject(index) ?: continue
                val name = item.optString("name").ifBlank { "Unidentified object" }
                val category = item.optString("category").ifBlank { "Observation" }
                val location = item.optString("relative_location").ifBlank { "Location unclear" }
                appendLine("- **$name** ($category) — $location · **${confidenceLabel(item.optString("confidence"))} confidence**")
                item.optString("visual_evidence").takeIf { it.isNotBlank() }?.let { appendLine("  - Evidence: $it") }
            }
            appendStringArray(report.optJSONArray("resource_targets"), "Resource targets")
            report.optJSONObject("recommended_entry")?.let { entry ->
                appendLine(); appendLine("### Recommended entry")
                appendLine("**${entry.optString("side").ifBlank { "Not determined" }}** · ${confidenceLabel(entry.optString("confidence"))} confidence")
                entry.optString("reason").takeIf { it.isNotBlank() }?.let { appendLine(it) }
            }
            appendStringArray(report.optJSONArray("attack_sequence"), "Suggested attack sequence")
            appendStringArray(report.optJSONArray("risks"), "Risks")
            appendStringArray(report.optJSONArray("limitations"), "Image limitations")
            report.optString("next_question").takeIf { it.isNotBlank() }?.let { appendLine(); appendLine("### One detail needed"); appendLine(it) }
            appendLine(); appendLine("_Visual confidence is the AI model qualitative estimate, not a measured probability. Verify uncertain observations before attacking._")
        }.trim()
    }
    private fun StringBuilder.appendStringArray(items: JSONArray?, heading: String) {
        if (items == null || items.length() == 0) return
        appendLine(); appendLine("### $heading")
        for (index in 0 until items.length()) { val value = items.optString(index).trim(); if (value.isNotBlank()) appendLine("- $value") }
    }
    private fun confidenceLabel(value: String): String = when (value.lowercase()) {
        "high" -> "High"; "medium" -> "Medium"; "low" -> "Low"; else -> "Unclear"
    }
}
