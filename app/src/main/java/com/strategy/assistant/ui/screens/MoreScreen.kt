
package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.ui.localization.appText

private val MoreBackground = Color(0xFF090710)
private val MoreGold = Color(0xFFD9B76E)
private val MorePurple = Color(0xFFB46BFF)
private val MoreText = Color(0xFFF5F0FA)
private val MoreMuted = Color(0xFFAAA0B9)
private val MoreLine = Color(0xFF30243D)

@Composable
fun MoreScreen(
    onOpenAccounts: () -> Unit,
    onOpenActivity: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenGameManagement: () -> Unit,
    onOpenStorage: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenAIControlPanel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoreBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp)
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        // BRAND
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF54258A),
                                Color(0xFF170D29)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        MoreGold.copy(alpha = 0.65f),
                        RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("♛", color = MoreGold, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "STRATEGY",
                    color = MoreText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    "ASSISTANT",
                    color = MoreGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        // USER PROFILE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF241633),
                            Color(0xFF120E1A)
                        )
                    )
                )
                .border(
                    1.dp,
                    MoreLine,
                    RoundedCornerShape(16.dp)
                )
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3A2850))
                    .border(
                        1.dp,
                        MoreGold.copy(alpha = 0.5f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "A",
                    color = MoreGold,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Ahmad",
                    color = MoreText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        appText("premium_user"),
                        color = MoreMuted,
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text("♛", color = MoreGold, fontSize = 12.sp)
                }
            }

        }

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            appText("management_section"),
            color = MoreGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        MoreMenuItem(
            symbol = "♟",
            title = appText("accounts"),
            subtitle = appText("manage_accounts_description"),
            onClick = onOpenAccounts
        )

        MoreMenuItem(
            symbol = "▥",
            title = appText("activity_center"),
            subtitle = appText("activity_description"),
            onClick = onOpenActivity
        )

        MoreMenuItem(
            symbol = "♢",
            title = appText("notifications"),
            subtitle = appText("notifications_description"),
            onClick = onOpenNotifications
        )

        MoreMenuItem(
            symbol = "🎮",
            title = appText("game_management"),
            subtitle = appText("game_management_description"),
            onClick = onOpenGameManagement
        )

        MoreMenuItem(
            symbol = "▤",
            title = appText("storage_backup"),
            subtitle = appText("storage_description"),
            onClick = onOpenStorage
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            appText("preferences"),
            color = MoreGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        MoreMenuItem(
            symbol = "✧",
            title = appText("ai_control_panel"),
            subtitle = appText("ai_control_description"),
            onClick = onOpenAIControlPanel
        )

        MoreMenuItem(
            symbol = "⚙",
            title = appText("settings"),
            subtitle = appText("app_preferences"),
            onClick = onOpenSettings
        )

        MoreMenuItem(
            symbol = "ⓘ",
            title = appText("about"),
            subtitle = appText("about_application_description"),
            onClick = onOpenAbout
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            "STRATEGY ASSISTANT",
            color = MoreMuted,
            fontSize = 9.sp,
            letterSpacing = 1.5.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            "VERSION 1.0.0",
            color = Color(0xFF665C73),
            fontSize = 8.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun MoreMenuItem(
    symbol: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 9.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(39.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(Color(0xFF241631))
                .border(
                    1.dp,
                    MorePurple.copy(alpha = 0.22f),
                    RoundedCornerShape(11.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                symbol,
                color = MorePurple,
                fontSize = 19.sp
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                color = MoreText,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                subtitle,
                color = MoreMuted,
                fontSize = 9.sp
            )
        }

        Text(
            "›",
            color = MoreMuted,
            fontSize = 23.sp
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp)
            .height(1.dp)
            .background(MoreLine.copy(alpha = 0.65f))
    )
}
