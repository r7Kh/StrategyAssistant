package com.strategy.assistant.data.ai

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject

private val Context.cocProfileDataStore by preferencesDataStore(name = "strategy_assistant_coc_profile")

enum class CocFarmingGoal(val label: String) {
    GOLD("Gold"), ELIXIR("Elixir"), DARK_ELIXIR("Dark Elixir"), BALANCED("Balanced resources")
}

data class CocPlayerProfile(
    val townHallLevel: Int? = null,
    val farmingGoal: CocFarmingGoal = CocFarmingGoal.BALANCED,
    val army: String = "",
    val heroesAndEquipment: String = "",
    val spellsAndSiege: String = "",
    val notes: String = ""
) {
    fun toPromptContext(): String {
        val details = listOf(
            townHallLevel?.let { "Town Hall: $it" },
            "Farming goal: ${farmingGoal.label}",
            army.trim().takeIf { it.isNotBlank() }?.let { "Available army (player supplied): ${it.take(700)}" },
            heroesAndEquipment.trim().takeIf { it.isNotBlank() }?.let { "Heroes and equipment (player supplied): ${it.take(500)}" },
            spellsAndSiege.trim().takeIf { it.isNotBlank() }?.let { "Spells and Siege Machine (player supplied): ${it.take(500)}" },
            notes.trim().takeIf { it.isNotBlank() }?.let { "Additional player notes: ${it.take(400)}" }
        ).filterNotNull()
        if (details.isEmpty()) return ""
        return buildString {
            appendLine("PLAYER CLASH OF CLANS PROFILE (user-provided; may be incomplete or outdated):")
            details.forEach { appendLine("- $it") }
            appendLine("Treat blank fields as unknown. Never infer missing levels, capacity, availability, or equipment. If the profile conflicts with the latest user message, prefer the latest message.")
        }
    }

    companion object {
        fun validate(profile: CocPlayerProfile): CocPlayerProfile {
            require(profile.townHallLevel == null || profile.townHallLevel in 2..19) {
                "Town Hall level must be between 2 and 19, or left unset."
            }
            require(profile.army.length <= 700) { "Army details must be 700 characters or fewer." }
            require(profile.heroesAndEquipment.length <= 500) { "Heroes and equipment must be 500 characters or fewer." }
            require(profile.spellsAndSiege.length <= 500) { "Spells and Siege Machine must be 500 characters or fewer." }
            require(profile.notes.length <= 400) { "Additional notes must be 400 characters or fewer." }
            return profile.copy(
                army = profile.army.trim(),
                heroesAndEquipment = profile.heroesAndEquipment.trim(),
                spellsAndSiege = profile.spellsAndSiege.trim(),
                notes = profile.notes.trim()
            )
        }
    }
}

class CocPlayerProfileRepository(context: Context) {
    private val store = context.applicationContext.cocProfileDataStore
    val profile: Flow<CocPlayerProfile> = store.data.map { decode(it[KEY].orEmpty()) }

    suspend fun save(profile: CocPlayerProfile) {
        val valid = CocPlayerProfile.validate(profile)
        val json = JSONObject().apply {
            put("townHallLevel", valid.townHallLevel ?: JSONObject.NULL)
            put("farmingGoal", valid.farmingGoal.name)
            put("army", valid.army)
            put("heroesAndEquipment", valid.heroesAndEquipment)
            put("spellsAndSiege", valid.spellsAndSiege)
            put("notes", valid.notes)
        }.toString()
        store.edit { it[KEY] = json }
    }

    suspend fun clear() {
        store.edit { it.remove(KEY) }
    }

    private fun decode(raw: String): CocPlayerProfile = runCatching {
        val json = JSONObject(raw)
        val level = if (json.isNull("townHallLevel")) null else json.optInt("townHallLevel").takeIf { it in 2..19 }
        val goal = runCatching { CocFarmingGoal.valueOf(json.optString("farmingGoal")) }.getOrDefault(CocFarmingGoal.BALANCED)
        CocPlayerProfile(
            townHallLevel = level,
            farmingGoal = goal,
            army = json.optString("army").take(700),
            heroesAndEquipment = json.optString("heroesAndEquipment").take(500),
            spellsAndSiege = json.optString("spellsAndSiege").take(500),
            notes = json.optString("notes").take(400)
        )
    }.getOrDefault(CocPlayerProfile())

    private companion object {
        val KEY = stringPreferencesKey("player_profile_json")
    }
}
