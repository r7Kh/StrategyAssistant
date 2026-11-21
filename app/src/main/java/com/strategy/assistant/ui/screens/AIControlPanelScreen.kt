
package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PanelGold = Color(0xFFD6B46A)
private val PanelBackground = Color(0xFF090811)
private val PanelCard = Color(0xFF151321)
private val PanelMuted = Color(0xFF9692A5)

@Composable
fun AIControlPanelScreen(
    padding: PaddingValues,
    onBack: () -> Unit
) {
    var localProcessing by remember { mutableStateOf(true) }
    var cloudProcessing by remember { mutableStateOf(false) }
    var autoAnalysis by remember { mutableStateOf(true) }
    var smartSuggestions by remember { mutableStateOf(true) }
    var privacyMode by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PanelBackground)
            .padding(padding)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Column {
                Text(
                    text = "AI CONTROL PANEL",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Manage your AI experience",
                    color = PanelMuted,
                    fontSize = 12.sp
                )
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            color = PanelCard
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(15.dp),
                        color = Color(0xFF29213C)
                    ) {
                        androidx.compose.foundation.layout.Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = PanelGold,
                                modifier = Modifier.size(27.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(start = 13.dp)
                    ) {
                        Text(
                            text = "AI Assistant",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "System ready",
                            color = Color(0xFF65C99A),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "AI SYSTEM STATUS",
                    color = PanelMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatusItem("Local AI", "Available")
                    StatusItem("Cloud AI", "Optional")
                    StatusItem("Mode", "Hybrid")
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        PanelSectionTitle("PROCESSING")

        PanelToggle(
            icon = Icons.Default.Memory,
            title = "Local AI Processing",
            description = "Process supported tasks directly on your device",
            checked = localProcessing,
            onCheckedChange = { localProcessing = it }
        )

        PanelToggle(
            icon = Icons.Default.Cloud,
            title = "Cloud AI Processing",
            description = "Allow cloud processing when available",
            checked = cloudProcessing,
            onCheckedChange = { cloudProcessing = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PanelSectionTitle("INTELLIGENCE")

        PanelToggle(
            icon = Icons.Default.Speed,
            title = "Automatic Analysis",
            description = "Analyze available game information automatically",
            checked = autoAnalysis,
            onCheckedChange = { autoAnalysis = it }
        )

        PanelToggle(
            icon = Icons.Default.AutoAwesome,
            title = "Smart Suggestions",
            description = "Receive strategic recommendations",
            checked = smartSuggestions,
            onCheckedChange = { smartSuggestions = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PanelSectionTitle("PRIVACY & SECURITY")

        PanelToggle(
            icon = Icons.Default.Security,
            title = "Privacy Mode",
            description = "Prefer on-device processing whenever possible",
            checked = privacyMode,
            onCheckedChange = { privacyMode = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFF211B30)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = PanelGold,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "AI options are currently local UI settings. Processing services will be connected later.",
                    color = PanelMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun PanelSectionTitle(title: String) {
    Text(
        text = title,
        color = PanelGold,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun StatusItem(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title,
            color = PanelMuted,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PanelToggle(
    icon: ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(15.dp),
        color = PanelCard
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PanelGold,
                modifier = Modifier.size(21.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    color = PanelMuted,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = PanelGold,
                    uncheckedThumbColor = PanelMuted,
                    uncheckedTrackColor = Color(0xFF393544)
                )
            )
        }
    }
}
