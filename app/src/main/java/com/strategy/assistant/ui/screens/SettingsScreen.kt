
package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import kotlinx.coroutines.launch
import com.strategy.assistant.data.preferences.AppPreferences
import com.strategy.assistant.data.preferences.AppPreferencesRepository
import com.strategy.assistant.ui.localization.appText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SettingsBackground = Color(0xFF090710)
private val SettingsSurface = Color(0xFF171220)
private val SettingsGold = Color(0xFFD9B76E)
private val SettingsPurple = Color(0xFFAD68FF)
private val SettingsText = Color(0xFFF5F0FA)
private val SettingsMuted = Color(0xFFAAA0B9)

@Composable
fun SettingsScreen(
    padding: PaddingValues,
    onBack: () -> Unit,
    onOpenStorage: () -> Unit = {}
) {
    val context = LocalContext.current
    val repository = remember(context) { AppPreferencesRepository(context) }
    val preferences by repository.preferences.collectAsState(initial = AppPreferences())
    val scope = rememberCoroutineScope()

    val notificationsEnabled = preferences.notificationsEnabled
    val soundEnabled = preferences.soundEnabled
    val vibrationEnabled = preferences.vibrationEnabled
    val selectedLanguage = preferences.language
    var languageMenuExpanded by remember { mutableStateOf(false) }

    val languages = listOf(
        "English",
        "العربية"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SettingsBackground)
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 19.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(SettingsSurface, CircleShape)
                    .border(1.dp, Color(0xFF49345F), CircleShape)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text("‹", color = SettingsText, fontSize = 27.sp)
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    appText("system_configuration"),
                    color = SettingsGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    appText("settings"),
                    color = SettingsText,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text("⚙", color = SettingsPurple, fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.height(25.dp))

        // GENERAL
        SettingsSectionTitle(appText("general"))

        SettingsGroup {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            languageMenuExpanded = true
                        }
                        .padding(vertical = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsIcon("文")

                    Spacer(modifier = Modifier.width(11.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            appText("language"),
                            color = SettingsText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            appText("choose_language"),
                            color = SettingsMuted,
                            fontSize = 9.sp
                        )
                    }

                    Box {
                        Text(
                            selectedLanguage,
                            color = SettingsGold,
                            fontSize = 10.sp,
                            modifier = Modifier.clickable {
                                languageMenuExpanded = true
                            }
                        )

                        DropdownMenu(
                            expanded = languageMenuExpanded,
                            onDismissRequest = {
                                languageMenuExpanded = false
                            },
                            containerColor = SettingsSurface
                        ) {
                            languages.forEach { language ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            language,
                                            color = SettingsText
                                        )
                                    },
                                    onClick = {
                                        scope.launch { repository.setLanguage(language) }
                                        languageMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(7.dp))
                    Text("⌄", color = SettingsMuted)
                }

                SettingsDivider()

                SettingsOption(
                    "◐",
                    appText("appearance"),
                    appText("appearance_description"),
                    appText("dark_mode")
                )

                SettingsDivider()

                SettingsOption(
                    "◷",
                    appText("date_time"),
                    appText("date_time_description"),
                    appText("system_default")
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // NOTIFICATIONS
        SettingsSectionTitle(appText("notifications"))

        SettingsGroup {
            SettingsToggle(
                "♢",
                appText("push_notifications"),
                appText("push_notifications_description"),
                notificationsEnabled
            ) { value -> scope.launch { repository.setNotificationsEnabled(value) } }

            SettingsDivider()

            SettingsToggle(
                "♫",
                appText("sound_effects"),
                appText("sound_effects_description"),
                soundEnabled
            ) { value -> scope.launch { repository.setSoundEnabled(value) } }

            SettingsDivider()

            SettingsToggle(
                "〰",
                appText("vibration"),
                appText("vibration_description"),
                vibrationEnabled
            ) { value -> scope.launch { repository.setVibrationEnabled(value) } }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PRIVACY
        SettingsSectionTitle(appText("privacy_data"))

        SettingsGroup {
            SettingsOption(
                "◈",
                appText("anonymous_analytics"),
                appText("analytics_description"),
                "Not active"
            )

            SettingsDivider()

            Row(
                modifier = Modifier.fillMaxWidth().clickable { onOpenStorage() }.padding(vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingsIcon("▣")
                Spacer(Modifier.width(11.dp))
                Column(Modifier.weight(1f)) {
                    Text(appText("data_management"), color = SettingsText, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(4.dp))
                    Text(appText("data_management_description"), color = SettingsMuted, fontSize = 9.sp)
                }
                Text(appText("manage"), color = SettingsGold, fontSize = 9.sp)
                Spacer(Modifier.width(5.dp))
                Text("›", color = SettingsMuted, fontSize = 19.sp)
            }

            SettingsDivider()

            SettingsOption(
                "♧",
                appText("privacy_policy"),
                appText("privacy_policy_description"),
                appText("view")
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // AI PREFERENCES
        SettingsSectionTitle(appText("ai_preferences"))

        SettingsGroup {
            SettingsToggle(
                "◉", appText("local_ai_processing"), appText("local_processing_description"),
                preferences.localProcessingEnabled
            ) { value -> scope.launch { repository.setLocalProcessingEnabled(value) } }

            SettingsDivider()

            SettingsToggle(
                "☁", appText("cloud_ai_processing"), appText("cloud_processing_description"),
                preferences.cloudProcessingEnabled
            ) { value -> scope.launch { repository.setCloudProcessingEnabled(value) } }

            SettingsDivider()

            SettingsToggle(
                "↻", appText("automatic_analysis"), appText("automatic_analysis_description"),
                preferences.automaticAnalysisEnabled
            ) { value -> scope.launch { repository.setAutomaticAnalysisEnabled(value) } }

            SettingsDivider()

            SettingsToggle(
                "✧", appText("smart_suggestions"), appText("smart_suggestions_description"),
                preferences.smartSuggestionsEnabled
            ) { value -> scope.launch { repository.setSmartSuggestionsEnabled(value) } }

            SettingsDivider()

            SettingsToggle(
                "◈", appText("privacy_mode"), appText("privacy_mode_description"),
                preferences.privacyModeEnabled
            ) { value -> scope.launch { repository.setPrivacyModeEnabled(value) } }

            Text(
                appText("ai_settings_notice"),
                color = SettingsMuted,
                fontSize = 9.sp,
                lineHeight = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SYSTEM
        SettingsSectionTitle(appText("system"))

        SettingsGroup {
            SettingsOption(
                "↻",
                appText("check_updates"),
                appText("check_updates_description"),
                appText("check")
            )

            SettingsDivider()

            SettingsOption(
                "ⓘ",
                appText("about_application"),
                appText("about_application_description"),
                appText("view")
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SettingsSurface, RoundedCornerShape(14.dp))
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(14.dp)
                )
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "STRATEGY ASSISTANT",
                color = SettingsGold,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                appText("multilingual_enabled"),
                color = SettingsMuted,
                fontSize = 9.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                "VERSION 1.0.0",
                color = SettingsPurple,
                fontSize = 8.sp,
                letterSpacing = 1.2.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    Text(
        title,
        color = SettingsGold,
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp
    )
    Spacer(modifier = Modifier.height(11.dp))
}

@Composable
private fun SettingsGroup(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SettingsSurface, RoundedCornerShape(15.dp))
            .border(
                1.dp,
                Color(0xFF30243D),
                RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 13.dp, vertical = 5.dp),
        content = content
    )
}

@Composable
private fun SettingsIcon(symbol: String) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .background(
                Color(0xFF2B1B3D),
                RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(symbol, color = SettingsPurple, fontSize = 18.sp)
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 43.dp)
            .height(1.dp)
            .background(Color(0xFF30243D))
    )
}

@Composable
private fun SettingsOption(
    symbol: String,
    title: String,
    description: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIcon(symbol)

        Spacer(modifier = Modifier.width(11.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                color = SettingsText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                description,
                color = SettingsMuted,
                fontSize = 9.sp
            )
        }

        Text(value, color = SettingsGold, fontSize = 9.sp)
        Spacer(modifier = Modifier.width(5.dp))
        Text("›", color = SettingsMuted, fontSize = 19.sp)
    }
}

@Composable
private fun SettingsToggle(
    symbol: String,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current
    val feedbackRepository = remember(context) { AppPreferencesRepository(context) }
    val feedbackPreferences by feedbackRepository.preferences.collectAsState(initial = AppPreferences())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIcon(symbol)

        Spacer(modifier = Modifier.width(11.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                color = SettingsText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                description,
                color = SettingsMuted,
                fontSize = 9.sp
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = { value ->
                if (feedbackPreferences.soundEnabled) view.playSoundEffect(SoundEffectConstants.CLICK)
                if (feedbackPreferences.vibrationEnabled) view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                onCheckedChange(value)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = SettingsPurple,
                uncheckedThumbColor = SettingsMuted,
                uncheckedTrackColor = Color(0xFF30243D),
                uncheckedBorderColor = Color(0xFF51445F)
            )
        )
    }
}
