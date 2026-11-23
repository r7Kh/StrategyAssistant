
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.ui.localization.appText

private val PanelGold = Color(0xFFD6B46A)
private val PanelBackground = Color(0xFF090811)
private val PanelCard = Color(0xFF151321)
private val PanelMuted = Color(0xFF9692A5)

@Composable
fun AIControlPanelScreen(
    padding: PaddingValues,
    onBack: () -> Unit
) {
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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = appText("back"),
                    tint = Color.White
                )
            }

            Column {
                Text(
                    text = appText("ai_control_panel"),
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = appText("manage_ai_experience"),
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
                            text = appText("ai_assistant"),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = appText("coming_soon"),
                            color = Color(0xFF65C99A),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = appText("ai_system_status"),
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
                    StatusItem(appText("local_ai"), appText("coming_soon"))
                    StatusItem(appText("cloud_ai"), appText("coming_soon"))
                    StatusItem(appText("mode"), appText("coming_soon"))
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = PanelCard
        ) {
            Column(Modifier.padding(18.dp)) {
                Text(appText("ai_engine_unavailable"), color = PanelGold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(appText("ai_settings_notice"), color = PanelMuted, fontSize = 11.sp, lineHeight = 17.sp)
                Spacer(Modifier.height(14.dp))
                listOf(
                    appText("local_ai_processing"),
                    appText("cloud_ai_processing"),
                    appText("automatic_analysis"),
                    appText("smart_suggestions"),
                    appText("privacy_mode")
                ).forEachIndexed { index, feature ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(feature, Modifier.weight(1f), color = Color.White, fontSize = 12.sp)
                        Text(appText("coming_soon"), color = PanelGold, fontSize = 10.sp)
                    }
                    if (index != 4) androidx.compose.material3.HorizontalDivider(color = Color(0xFF393544))
                }
            }
        }

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
                    text = appText("ai_settings_notice"),
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

