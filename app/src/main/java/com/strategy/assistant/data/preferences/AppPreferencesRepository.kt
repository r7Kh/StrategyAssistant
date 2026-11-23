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
    val analyticsEnabled: Boolean = false,
    val localProcessingEnabled: Boolean = true,
    val cloudProcessingEnabled: Boolean = false,
    val automaticAnalysisEnabled: Boolean = true,
    val smartSuggestionsEnabled: Boolean = true,
    val privacyModeEnabled: Boolean = true,
    val use24HourTime: Boolean = true
)

class AppPreferencesRepository(context: Context) {
    private val dataStore = context.applicationContext.settingsDataStore

    val preferences: Flow<AppPreferences> = dataStore.data.map { values ->
        AppPreferences(
            language = values[Keys.language] ?: "English",
            notificationsEnabled = values[Keys.notifications] ?: true,
            soundEnabled = values[Keys.sound] ?: true,
            analyticsEnabled = values[Keys.analytics] ?: false,
            localProcessingEnabled = values[Keys.localProcessing] ?: true,
            cloudProcessingEnabled = values[Keys.cloudProcessing] ?: false,
            automaticAnalysisEnabled = values[Keys.automaticAnalysis] ?: true,
            smartSuggestionsEnabled = values[Keys.smartSuggestions] ?: true,
            privacyModeEnabled = values[Keys.privacyMode] ?: true,
            use24HourTime = values[Keys.use24HourTime] ?: true
        )
    }

    suspend fun setLanguage(value: String) = dataStore.edit { it[Keys.language] = value }
    suspend fun setNotificationsEnabled(value: Boolean) = dataStore.edit { it[Keys.notifications] = value }
    suspend fun setSoundEnabled(value: Boolean) = dataStore.edit { it[Keys.sound] = value }
    suspend fun setAnalyticsEnabled(value: Boolean) = dataStore.edit { it[Keys.analytics] = value }
    suspend fun setLocalProcessingEnabled(value: Boolean) = dataStore.edit { it[Keys.localProcessing] = value }
    suspend fun setCloudProcessingEnabled(value: Boolean) = dataStore.edit { it[Keys.cloudProcessing] = value }
    suspend fun setAutomaticAnalysisEnabled(value: Boolean) = dataStore.edit { it[Keys.automaticAnalysis] = value }
    suspend fun setSmartSuggestionsEnabled(value: Boolean) = dataStore.edit { it[Keys.smartSuggestions] = value }
    suspend fun setPrivacyModeEnabled(value: Boolean) = dataStore.edit { it[Keys.privacyMode] = value }
    suspend fun setUse24HourTime(value: Boolean) = dataStore.edit { it[Keys.use24HourTime] = value }

    private object Keys {
        val language = stringPreferencesKey("language")
        val notifications = booleanPreferencesKey("notifications_enabled")
        val sound = booleanPreferencesKey("sound_enabled")
        val analytics = booleanPreferencesKey("analytics_enabled")
        val localProcessing = booleanPreferencesKey("local_processing_enabled")
        val cloudProcessing = booleanPreferencesKey("cloud_processing_enabled")
        val automaticAnalysis = booleanPreferencesKey("automatic_analysis_enabled")
        val smartSuggestions = booleanPreferencesKey("smart_suggestions_enabled")
        val privacyMode = booleanPreferencesKey("privacy_mode_enabled")
        val use24HourTime = booleanPreferencesKey("use_24_hour_time")
    }
}
