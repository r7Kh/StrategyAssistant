package com.strategy.assistant

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.data.core.CoreSystemsRepository
import com.strategy.assistant.data.preferences.AppPreferences
import com.strategy.assistant.data.preferences.AppPreferencesRepository
import com.strategy.assistant.ui.localization.LocalAppLanguage
import com.strategy.assistant.ui.screens.AICenterScreen
import com.strategy.assistant.ui.screens.AIControlPanelScreen
import com.strategy.assistant.ui.screens.AboutScreen
import com.strategy.assistant.ui.screens.AccountsScreen
import com.strategy.assistant.ui.screens.ActivityScreen
import com.strategy.assistant.ui.screens.GameLibraryScreen
import com.strategy.assistant.ui.screens.GameManagementScreen
import com.strategy.assistant.ui.screens.MoreScreen
import com.strategy.assistant.ui.screens.NotificationsScreen
import com.strategy.assistant.ui.screens.PremiumHomeScreen
import com.strategy.assistant.ui.screens.SettingsScreen
import com.strategy.assistant.ui.screens.StorageBackupScreen
import com.strategy.assistant.ui.theme.StrategyAssistantTheme
import kotlinx.coroutines.launch
import java.io.File

private val AppBackground = Color(0xFF090710)
private val GoldAccent = Color(0xFFD6B56D)
private val SecondaryText = Color(0xFFAAA0B9)
private val PurpleIndicator = Color(0xFF34204F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { StrategyAssistantTheme { StrategyAssistantApp() } }
    }
}

@Composable
fun StrategyAssistantApp() {
    val context = LocalContext.current
    val preferencesRepository = remember(context) { AppPreferencesRepository(context) }
    val appPreferences by preferencesRepository.preferences.collectAsState(initial = AppPreferences())
    val coreRepository = remember(context) { CoreSystemsRepository(context) }
    val accounts by coreRepository.accounts.collectAsState(initial = emptyList())
    val activityEntries by coreRepository.activities.collectAsState(initial = emptyList())
    val notifications by coreRepository.notifications.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var cacheBytes by remember { mutableStateOf(0L) }
    var appBytes by remember { mutableStateOf(0L) }

    val backupLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->
        if (uri != null) scope.launch {
            runCatching {
                val backup = coreRepository.exportBackup()
                context.contentResolver.openOutputStream(uri)?.use { output ->
                    backup.inputStream().use { it.copyTo(output) }
                } ?: error("Could not open destination")
            }.onSuccess {
                Toast.makeText(context, "Backup exported successfully", Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(context, "Backup failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    val restoreLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) scope.launch {
            runCatching {
                val file = File(context.cacheDir, "restore-import.json")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    file.outputStream().use { input.copyTo(it) }
                } ?: error("Could not read selected file")
                coreRepository.importBackup(file)
                file.delete()
            }.onSuccess {
                Toast.makeText(context, "Backup restored successfully", Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(context, "Restore failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    val isArabic = appPreferences.language == "العربية"
    var currentScreen by remember { mutableIntStateOf(0) }
    val navigationItems = listOf(
        Triple("⌂", if (isArabic) "الرئيسية" else "Home", 0),
        Triple("✧", if (isArabic) "الذكاء" else "AI", 1),
        Triple("▣", if (isArabic) "الألعاب" else "Games", 2),
        Triple("☰", if (isArabic) "المزيد" else "More", 3)
    )
    val selectedNavigationTab = if (currentScreen >= 4) 3 else currentScreen

    CompositionLocalProvider(
        LocalAppLanguage provides appPreferences.language,
        LocalLayoutDirection provides if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Scaffold(
            containerColor = AppBackground,
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF100C18), contentColor = Color.White) {
                    navigationItems.forEach { item ->
                        NavigationBarItem(
                            selected = selectedNavigationTab == item.third,
                            onClick = { currentScreen = item.third },
                            icon = { Text(item.first, fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                            label = { Text(item.second, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = GoldAccent,
                                selectedTextColor = GoldAccent,
                                unselectedIconColor = SecondaryText,
                                unselectedTextColor = SecondaryText,
                                indicatorColor = PurpleIndicator
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            when (currentScreen) {
                0 -> PremiumHomeScreen(
                    padding = innerPadding,
                    onOpenAi = { currentScreen = 1 },
                    onOpenLibrary = { currentScreen = 2 },
                    onOpenActivity = { currentScreen = 5 },
                    onOpenManagement = { currentScreen = 7 }
                )
                1 -> Box(Modifier.fillMaxSize().padding(innerPadding)) { AICenterScreen() }
                2 -> GameLibraryScreen(padding = innerPadding)
                3 -> Box(Modifier.fillMaxSize().padding(innerPadding)) {
                    MoreScreen(
                        onOpenAccounts = { currentScreen = 4 },
                        onOpenActivity = { currentScreen = 5 },
                        onOpenNotifications = { currentScreen = 6 },
                        onOpenGameManagement = { currentScreen = 7 },
                        onOpenStorage = { currentScreen = 8 },
                        onOpenSettings = { currentScreen = 9 },
                        onOpenAbout = { currentScreen = 10 },
                        onOpenAIControlPanel = { currentScreen = 11 }
                    )
                }
                4 -> AccountsScreen(
                    padding = innerPadding,
                    onBack = { currentScreen = 3 },
                    accounts = accounts,
                    onAdd = { title, platform, username, notes ->
                        scope.launch {
                            coreRepository.addAccount(title, platform, username, notes)
                            if (appPreferences.notificationsEnabled) coreRepository.addNotification("Accounts", "Account added", title)
                        }
                    },
                    onUpdate = { account -> scope.launch { coreRepository.updateAccount(account) } },
                    onActivate = { id ->
                        scope.launch {
                            coreRepository.setActiveAccount(id)
                            if (appPreferences.notificationsEnabled) coreRepository.addNotification(
                                "Accounts", "Active account changed", accounts.firstOrNull { it.id == id }?.title.orEmpty()
                            )
                        }
                    },
                    onRemove = { id ->
                        scope.launch {
                            val title = accounts.firstOrNull { it.id == id }?.title.orEmpty()
                            coreRepository.removeAccount(id)
                            if (appPreferences.notificationsEnabled) coreRepository.addNotification("Accounts", "Account removed", title)
                        }
                    }
                )
                5 -> ActivityScreen(
                    padding = innerPadding,
                    onBack = { currentScreen = 3 },
                    entries = activityEntries,
                    onClear = { scope.launch { coreRepository.clearActivities() } }
                )
                6 -> NotificationsScreen(
                    padding = innerPadding,
                    onBack = { currentScreen = 3 },
                    notifications = notifications,
                    onMarkRead = { id -> scope.launch { coreRepository.markNotificationRead(id) } },
                    onMarkAllRead = { scope.launch { coreRepository.markAllNotificationsRead() } },
                    onDelete = { id -> scope.launch { coreRepository.removeNotification(id) } },
                    onClear = { scope.launch { coreRepository.clearNotifications() } }
                )
                7 -> GameManagementScreen(padding = innerPadding, onBack = { currentScreen = 3 })
                8 -> StorageBackupScreen(
                    padding = innerPadding,
                    onBack = { currentScreen = 3 },
                    cacheBytes = cacheBytes,
                    appBytes = appBytes,
                    onRefresh = {
                        scope.launch {
                            cacheBytes = coreRepository.cacheSizeBytes()
                            appBytes = coreRepository.appStorageSizeBytes()
                        }
                    },
                    onBackup = { backupLauncher.launch("strategy-assistant-backup.json") },
                    onRestore = { restoreLauncher.launch(arrayOf("application/json", "text/json", "*/*")) },
                    onCleanCache = {
                        scope.launch {
                            coreRepository.clearTemporaryCache()
                            cacheBytes = coreRepository.cacheSizeBytes()
                            appBytes = coreRepository.appStorageSizeBytes()
                        }
                    }
                )
                9 -> SettingsScreen(
                    padding = innerPadding,
                    onBack = { currentScreen = 3 },
                    onOpenStorage = { currentScreen = 8 }
                )
                10 -> AboutScreen(padding = innerPadding, onBack = { currentScreen = 3 })
                11 -> AIControlPanelScreen(padding = innerPadding, onBack = { currentScreen = 3 })
                else -> { currentScreen = 0 }
            }
        }
    }
}
