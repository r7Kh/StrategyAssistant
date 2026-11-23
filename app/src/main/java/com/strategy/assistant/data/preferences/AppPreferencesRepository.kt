package com.strategy.assistant.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "strategy_assistant_settings")

data class AppPreferences(
    val language: String = "English",
    val notificationsEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val use24HourTime: Boolean = true
)

class AppPreferencesRepository(context: Context) {
    private val dataStore = context.applicationContext.settingsDataStore

    val preferences: Flow<AppPreferences> = dataStore.data.map { values ->
        AppPreferences(
            language = values[Keys.language] ?: "English",
            notificationsEnabled = values[Keys.notifications] ?: true,
            soundEnabled = values[Keys.sound] ?: true,
            use24HourTime = values[Keys.use24HourTime] ?: true
        )
    }

    suspend fun setLanguage(value: String) = dataStore.edit { it[Keys.language] = value }
    suspend fun setNotificationsEnabled(value: Boolean) = dataStore.edit { it[Keys.notifications] = value }
    suspend fun setSoundEnabled(value: Boolean) = dataStore.edit { it[Keys.sound] = value }
    suspend fun setUse24HourTime(value: Boolean) = dataStore.edit { it[Keys.use24HourTime] = value }

    private object Keys {
        val language = stringPreferencesKey("language")
        val notifications = booleanPreferencesKey("notifications_enabled")
        val sound = booleanPreferencesKey("sound_enabled")
        val use24HourTime = booleanPreferencesKey("use_24_hour_time")
    }
}
