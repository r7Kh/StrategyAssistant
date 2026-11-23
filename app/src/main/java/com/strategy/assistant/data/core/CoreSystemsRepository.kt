package com.strategy.assistant.data.core

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.strategy.assistant.data.preferences.AppPreferencesRepository
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

private val Context.coreDataStore by preferencesDataStore(name = "strategy_assistant_core")

data class GamingAccount(
    val id: String,
    val title: String,
    val platform: String,
    val username: String,
    val notes: String,
    val isActive: Boolean,
    val createdAt: Long
)

data class ActivityEntry(
    val id: String,
    val category: String,
    val title: String,
    val details: String,
    val timestamp: Long
)

data class AppNotification(
    val id: String,
    val category: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean
)

data class CoreDataSnapshot(
    val accounts: List<GamingAccount>,
    val activities: List<ActivityEntry>,
    val notifications: List<AppNotification>
)

class CoreSystemsRepository(context: Context) {
    private val appContext = context.applicationContext
    private val store = appContext.coreDataStore
    private val settingsRepository = AppPreferencesRepository(appContext)

    val accounts: Flow<List<GamingAccount>> = store.data.map { decodeAccounts(it[Keys.accounts].orEmpty()) }
    val activities: Flow<List<ActivityEntry>> = store.data.map { decodeActivities(it[Keys.activities].orEmpty()) }
    val notifications: Flow<List<AppNotification>> = store.data.map { decodeNotifications(it[Keys.notifications].orEmpty()) }

    suspend fun addAccount(title: String, platform: String, username: String, notes: String) {
        require(title.isNotBlank()) { "Account name is required" }
        val account = GamingAccount(UUID.randomUUID().toString(), title.trim(), platform.trim(),
            username.trim(), notes.trim(), false, System.currentTimeMillis())
        store.edit { prefs ->
            val updated = decodeAccounts(prefs[Keys.accounts].orEmpty()) + account
            prefs[Keys.accounts] = encodeAccounts(updated)
        }
        log("Accounts", "Account added", "${account.title} (${account.platform})")
    }

    suspend fun setActiveAccount(id: String) {
        store.edit { prefs ->
            prefs[Keys.accounts] = encodeAccounts(decodeAccounts(prefs[Keys.accounts].orEmpty())
                .map { it.copy(isActive = it.id == id) })
        }
        log("Accounts", "Active account changed", "Selected account")
    }

    suspend fun updateAccount(account: GamingAccount) {
        store.edit { prefs ->
            prefs[Keys.accounts] = encodeAccounts(decodeAccounts(prefs[Keys.accounts].orEmpty())
                .map { if (it.id == account.id) account else it })
        }
        log("Accounts", "Account updated", account.title)
    }

    suspend fun removeAccount(id: String) {
        store.edit { prefs ->
            val existing = decodeAccounts(prefs[Keys.accounts].orEmpty())
            val title = existing.firstOrNull { it.id == id }?.title ?: "Account"
            prefs[Keys.accounts] = encodeAccounts(existing.filterNot { it.id == id })
            prefs[Keys.activities] = encodeActivities(
                (decodeActivities(prefs[Keys.activities].orEmpty()) +
                    ActivityEntry(UUID.randomUUID().toString(), "Accounts", "Account removed", title, System.currentTimeMillis()))
                    .take(500)
            )
        }
    }

    suspend fun log(category: String, title: String, details: String = "") {
        store.edit { prefs ->
            val current = decodeActivities(prefs[Keys.activities].orEmpty())
            val entry = ActivityEntry(UUID.randomUUID().toString(), category, title, details, System.currentTimeMillis())
            prefs[Keys.activities] = encodeActivities((listOf(entry) + current).take(500))
        }
    }

    suspend fun clearActivities() {
        store.edit { it[Keys.activities] = "[]" }
    }

    suspend fun addNotification(category: String, title: String, message: String) {
        store.edit { prefs ->
            val current = decodeNotifications(prefs[Keys.notifications].orEmpty())
            val item = AppNotification(UUID.randomUUID().toString(), category, title, message,
                System.currentTimeMillis(), false)
            prefs[Keys.notifications] = encodeNotifications((listOf(item) + current).take(300))
        }
        log("System", "Notification received", title)
    }

    suspend fun markNotificationRead(id: String) {
        store.edit { prefs ->
            prefs[Keys.notifications] = encodeNotifications(decodeNotifications(prefs[Keys.notifications].orEmpty())
                .map { if (it.id == id) it.copy(isRead = true) else it })
        }
    }

    suspend fun markAllNotificationsRead() {
        store.edit { prefs ->
            prefs[Keys.notifications] = encodeNotifications(decodeNotifications(prefs[Keys.notifications].orEmpty())
                .map { it.copy(isRead = true) })
        }
    }

    suspend fun removeNotification(id: String) {
        store.edit { prefs ->
            prefs[Keys.notifications] = encodeNotifications(decodeNotifications(prefs[Keys.notifications].orEmpty())
                .filterNot { it.id == id })
        }
    }

    suspend fun clearNotifications() {
        store.edit { it[Keys.notifications] = "[]" }
    }

    suspend fun exportBackup(): File {
        val prefs = store.data.first()
        val settings = settingsRepository.preferences.first()
        val settingsJson = JSONObject().apply {
            put("language", settings.language)
            put("notificationsEnabled", settings.notificationsEnabled)
            put("soundEnabled", settings.soundEnabled)
            put("analyticsEnabled", settings.analyticsEnabled)
            put("localProcessingEnabled", settings.localProcessingEnabled)
            put("cloudProcessingEnabled", settings.cloudProcessingEnabled)
            put("automaticAnalysisEnabled", settings.automaticAnalysisEnabled)
            put("smartSuggestionsEnabled", settings.smartSuggestionsEnabled)
            put("privacyModeEnabled", settings.privacyModeEnabled)
            put("use24HourTime", settings.use24HourTime)
        }
        val json = JSONObject().apply {
            put("format", "strategy-assistant-backup")
            put("version", 1)
            put("createdAt", System.currentTimeMillis())
            put("accounts", JSONArray(prefs[Keys.accounts] ?: "[]"))
            put("activities", JSONArray(prefs[Keys.activities] ?: "[]"))
            put("notifications", JSONArray(prefs[Keys.notifications] ?: "[]"))
            put("settings", settingsJson)
        }.toString(2)
        val directory = File(appContext.cacheDir, "backups").apply { mkdirs() }
        val stamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        return withContext(Dispatchers.IO) {
            File(directory, "strategy-assistant-$stamp.json").apply { writeText(json) }
        }
    }

    suspend fun importBackup(file: File) {
        val backupText = withContext(Dispatchers.IO) { file.readText() }
        val root = JSONObject(backupText)
        require(root.optString("format") == "strategy-assistant-backup") { "Unsupported backup file" }
        require(root.optInt("version") == 1) { "Unsupported backup version" }
        val accountsJson = root.optJSONArray("accounts") ?: JSONArray()
        val activitiesJson = root.optJSONArray("activities") ?: JSONArray()
        val notificationsJson = root.optJSONArray("notifications") ?: JSONArray()
        val settings = root.optJSONObject("settings")
        store.edit { prefs ->
            prefs[Keys.accounts] = accountsJson.toString()
            prefs[Keys.activities] = activitiesJson.toString()
            prefs[Keys.notifications] = notificationsJson.toString()
        }
        if (settings != null) {
            settingsRepository.setLanguage(settings.optString("language", "English"))
            settingsRepository.setNotificationsEnabled(settings.optBoolean("notificationsEnabled", true))
            settingsRepository.setSoundEnabled(settings.optBoolean("soundEnabled", true))
            settingsRepository.setAnalyticsEnabled(settings.optBoolean("analyticsEnabled", false))
            settingsRepository.setLocalProcessingEnabled(settings.optBoolean("localProcessingEnabled", true))
            settingsRepository.setCloudProcessingEnabled(settings.optBoolean("cloudProcessingEnabled", false))
            settingsRepository.setAutomaticAnalysisEnabled(settings.optBoolean("automaticAnalysisEnabled", true))
            settingsRepository.setSmartSuggestionsEnabled(settings.optBoolean("smartSuggestionsEnabled", true))
            settingsRepository.setPrivacyModeEnabled(settings.optBoolean("privacyModeEnabled", true))
            settingsRepository.setUse24HourTime(settings.optBoolean("use24HourTime", true))
        }
        log("System", "Backup restored", file.name)
    }

    suspend fun cacheSizeBytes(): Long = withContext(Dispatchers.IO) {
        appContext.cacheDir.walkTopDown().filter { it.isFile && it.parentFile?.name != "backups" }.sumOf { it.length() }
    }

    suspend fun appStorageSizeBytes(): Long = withContext(Dispatchers.IO) {
        appContext.dataDir.walkTopDown().filter { it.isFile }.sumOf { it.length() }
    }

    suspend fun clearTemporaryCache(): Long = withContext(Dispatchers.IO) {
        val cacheRoot = appContext.cacheDir
        val backupsDirectory = File(cacheRoot, "backups").canonicalFile
        var deletedBytes = 0L
        var failedItems = 0
        cacheRoot.listFiles()?.forEach { item ->
            if (item.canonicalFile != backupsDirectory) {
                val size = item.walkTopDown().filter { it.isFile }.sumOf { it.length() }
                if (item.deleteRecursively()) deletedBytes += size else failedItems++
            }
        }
        if (failedItems > 0) throw IllegalStateException("Could not remove $failedItems cache item(s)")
        deletedBytes
    }.also { deleted ->
        log("System", "Temporary cache cleared", "$deleted bytes released")
    }

    suspend fun backupSummary(): CoreDataSnapshot {
        val values = store.data.first()
        return CoreDataSnapshot(
            decodeAccounts(values[Keys.accounts].orEmpty()),
            decodeActivities(values[Keys.activities].orEmpty()),
            decodeNotifications(values[Keys.notifications].orEmpty())
        )
    }

    private object Keys {
        val accounts = stringPreferencesKey("accounts_json")
        val activities = stringPreferencesKey("activities_json")
        val notifications = stringPreferencesKey("notifications_json")
    }

    private fun encodeAccounts(items: List<GamingAccount>) = JSONArray().apply {
        items.forEach { item -> put(JSONObject().apply {
            put("id", item.id); put("title", item.title); put("platform", item.platform)
            put("username", item.username); put("notes", item.notes); put("isActive", item.isActive)
            put("createdAt", item.createdAt)
        }) }
    }.toString()

    private fun decodeAccounts(raw: String): List<GamingAccount> = runCatching {
        val array = JSONArray(raw)
        (0 until array.length()).map { i -> array.getJSONObject(i).let {
            GamingAccount(it.optString("id"), it.optString("title"), it.optString("platform"),
                it.optString("username"), it.optString("notes"), it.optBoolean("isActive"),
                it.optLong("createdAt"))
        } }
    }.getOrDefault(emptyList())

    private fun encodeActivities(items: List<ActivityEntry>) = JSONArray().apply {
        items.forEach { item -> put(JSONObject().apply {
            put("id", item.id); put("category", item.category); put("title", item.title)
            put("details", item.details); put("timestamp", item.timestamp)
        }) }
    }.toString()

    private fun decodeActivities(raw: String): List<ActivityEntry> = runCatching {
        val array = JSONArray(raw)
        (0 until array.length()).map { i -> array.getJSONObject(i).let {
            ActivityEntry(it.optString("id"), it.optString("category"), it.optString("title"),
                it.optString("details"), it.optLong("timestamp"))
        } }
    }.getOrDefault(emptyList())

    private fun encodeNotifications(items: List<AppNotification>) = JSONArray().apply {
        items.forEach { item -> put(JSONObject().apply {
            put("id", item.id); put("category", item.category); put("title", item.title)
            put("message", item.message); put("timestamp", item.timestamp); put("isRead", item.isRead)
        }) }
    }.toString()

    private fun decodeNotifications(raw: String): List<AppNotification> = runCatching {
        val array = JSONArray(raw)
        (0 until array.length()).map { i -> array.getJSONObject(i).let {
            AppNotification(it.optString("id"), it.optString("category"), it.optString("title"),
                it.optString("message"), it.optLong("timestamp"), it.optBoolean("isRead"))
        } }
    }.getOrDefault(emptyList())
}
