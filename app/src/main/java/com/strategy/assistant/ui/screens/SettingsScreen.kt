
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
    onBack: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(false) }
    var analyticsEnabled by remember { mutableStateOf(false) }

    var selectedLanguage by remember { mutableStateOf("English") }
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
                    "SYSTEM CONFIGURATION",
                    color = SettingsGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    "Settings",
                    color = SettingsText,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text("⚙", color = SettingsPurple, fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.height(25.dp))

        // GENERAL
        SettingsSectionTitle("GENERAL")

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
                            "Language",
                            color = SettingsText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Choose your application language",
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
                                        selectedLanguage = language
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
                    "Appearance",
                    "Application visual theme",
                    "Dark Mode"
                )

                SettingsDivider()

                SettingsOption(
                    "◷",
                    "Date & Time",
                    "Regional date and time format",
                    "System Default"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // NOTIFICATIONS
        SettingsSectionTitle("NOTIFICATIONS")

        SettingsGroup {
            SettingsToggle(
                "♢",
                "Push Notifications",
                "Receive important application alerts",
                notificationsEnabled
            ) { notificationsEnabled = it }

            SettingsDivider()

            SettingsToggle(
                "♫",
                "Sound Effects",
                "Application sounds and audio feedback",
                soundEnabled
            ) { soundEnabled = it }

            SettingsDivider()

            SettingsToggle(
                "〰",
                "Vibration",
                "Haptic feedback for interactions",
                vibrationEnabled
            ) { vibrationEnabled = it }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PRIVACY
        SettingsSectionTitle("PRIVACY & DATA")

        SettingsGroup {
            SettingsToggle(
                "◈",
                "Anonymous Analytics",
                "Allow anonymous usage statistics",
                analyticsEnabled
            ) { analyticsEnabled = it }

            SettingsDivider()

            SettingsOption(
                "▣",
                "Data Management",
                "Review your stored application data",
                "Manage"
            )

            SettingsDivider()

            SettingsOption(
                "♧",
                "Privacy Policy",
                "Learn how your data is handled",
                "View"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // AI PREFERENCES
        SettingsSectionTitle("AI PREFERENCES")

        SettingsGroup {
            SettingsOption(
                "✧",
                "AI Configuration",
                "Configure assistant behavior",
                "Configure"
            )

            SettingsDivider()

            SettingsOption(
                "◉",
                "AI Processing",
                "On-device and cloud processing preferences",
                "Local First"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SYSTEM
        SettingsSectionTitle("SYSTEM")

        SettingsGroup {
            SettingsOption(
                "↻",
                "Check for Updates",
                "Review available application updates",
                "Check"
            )

            SettingsDivider()

            SettingsOption(
                "ⓘ",
                "About Application",
                "Version and application information",
                "View"
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
                "Multilingual interface planned",
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
            onCheckedChange = onCheckedChange,
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
