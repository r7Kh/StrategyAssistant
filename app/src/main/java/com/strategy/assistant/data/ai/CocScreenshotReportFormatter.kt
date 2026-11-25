package com.strategy.assistant.data.ai

import org.json.JSONArray
import org.json.JSONObject

/** Converts model screenshot JSON into readable text; confidence is not a calibrated probability. */
internal object CocScreenshotReportFormatter {
    fun formatOrFallback(raw: String): String {
        val jsonText = extractJsonObject(raw) ?: return raw
        val report = runCatching { JSONObject(jsonText) }.getOrNull() ?: return raw
        if (!report.has("observations") && !report.has("screenshot_context")) return raw
        return buildString {
            appendLine("## Village screenshot report")
            report.safeString("screenshot_context").takeIf { it.isNotBlank() }?.let {
                appendLine()
                appendLine("**Screenshot type:** $it")
            }
            val observations = report.optJSONArray("observations") ?: JSONArray()
            appendLine()
            appendLine("### Visual observations")
            var renderedObservations = 0
            for (index in 0 until observations.length()) {
                val item = observations.optJSONObject(index) ?: continue
                val name = item.safeString("name").ifBlank { "Unidentified object" }
                val category = item.safeString("category").ifBlank { "Observation" }
                val location = item.safeString("relative_location").ifBlank { "Location unclear" }
                appendLine("- **$name** ($category) — $location · **${confidenceLabel(item.safeString("confidence"))} confidence**")
                item.safeString("visual_evidence").takeIf { it.isNotBlank() }?.let { appendLine("  - Evidence: $it") }
                renderedObservations++
            }
            if (renderedObservations == 0) appendLine("No reliable building observations could be identified.")
            appendStringArray(report.optJSONArray("resource_targets"), "Resource targets")
            report.optJSONObject("recommended_entry")?.let { entry ->
                appendLine()
                appendLine("### Recommended entry")
                appendLine("**${entry.safeString("side").ifBlank { "Not determined" }}** · ${confidenceLabel(entry.safeString("confidence"))} confidence")
                entry.safeString("reason").takeIf { it.isNotBlank() }?.let { appendLine(it) }
            }
            appendStringArray(report.optJSONArray("attack_sequence"), "Suggested attack sequence")
            appendStringArray(report.optJSONArray("risks"), "Risks")
            appendStringArray(report.optJSONArray("limitations"), "Image limitations")
            report.safeString("next_question").takeIf { it.isNotBlank() }?.let {
                appendLine()
                appendLine("### One detail needed")
                appendLine(it)
            }
            appendLine()
            appendLine("_Visual confidence is the AI model qualitative estimate, not a measured probability. Verify uncertain observations before attacking._")
        }.trim()
    }

    private fun extractJsonObject(raw: String): String? {
        val trimmed = raw.trim()
        val unfenced = trimmed
            .removePrefix("```json").removePrefix("```JSON").removePrefix("```")
            .removeSuffix("```").trim()
        val start = unfenced.indexOf('{')
        if (start < 0) return null
        var depth = 0
        var inString = false
        var escaped = false
        for (index in start until unfenced.length) {
            val char = unfenced[index]
            if (inString) {
                if (escaped) escaped = false
                else if (char == '\\\\') escaped = true
                else if (char == '\"') inString = false
            } else {
                when (char) {
                    '\"' -> inString = true
                    '{' -> depth++
                    '}' -> {
                        depth--
                        if (depth == 0) return unfenced.substring(start, index + 1)
                    }
                }
            }
        }
        return null
    }

    private fun JSONObject.safeString(key: String): String {
        if (!has(key) || isNull(key)) return ""
        return opt(key)?.toString()?.takeUnless { it == "null" }?.trim().orEmpty()
    }

    private fun StringBuilder.appendStringArray(items: JSONArray?, heading: String) {
        if (items == null || items.length() == 0) return
        val values = (0 until items.length()).mapNotNull { index ->
            if (items.isNull(index)) null else items.optString(index).trim().takeIf { it.isNotBlank() && it != "null" }
        }
        if (values.isEmpty()) return
        appendLine()
        appendLine("### $heading")
        values.forEach { appendLine("- $it") }
    }

    private fun confidenceLabel(value: String): String = when (value.lowercase()) {
        "high" -> "High"
        "medium" -> "Medium"
        "low" -> "Low"
        else -> "Unclear"
    }
}
