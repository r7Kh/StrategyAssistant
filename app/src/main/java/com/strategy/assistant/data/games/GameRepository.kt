package com.strategy.assistant.data.games

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray

private val Context.gamesDataStore by preferencesDataStore(name = "strategy_assistant_games")

class GameRepository(context: Context) {
    private val store = context.applicationContext.gamesDataStore
    private val gamesKey = stringPreferencesKey("saved_games")

    val games: Flow<List<String>> = store.data.map { prefs ->
        runCatching {
            val array = JSONArray(prefs[gamesKey] ?: "[]")
            List(array.length()) { index -> array.getString(index) }
        }.getOrDefault(emptyList())
    }

    suspend fun addGame(name: String) {
        val cleaned = name.trim()
        if (cleaned.isBlank()) return
        store.edit { prefs ->
            val current = runCatching {
                val array = JSONArray(prefs[gamesKey] ?: "[]")
                List(array.length()) { index -> array.getString(index) }
            }.getOrDefault(emptyList())
            if (current.none { it.equals(cleaned, ignoreCase = true) }) {
                prefs[gamesKey] = JSONArray((current + cleaned).sorted()).toString()
            }
        }
    }

    suspend fun removeGame(name: String) {
        store.edit { prefs ->
            val current = runCatching {
                val array = JSONArray(prefs[gamesKey] ?: "[]")
                List(array.length()) { index -> array.getString(index) }
            }.getOrDefault(emptyList())
            prefs[gamesKey] = JSONArray(current.filterNot { it == name }).toString()
        }
    }
}
