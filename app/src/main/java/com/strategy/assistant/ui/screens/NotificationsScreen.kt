
package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val NotificationBackground = Color(0xFF090710)
private val NotificationSurface = Color(0xFF171220)
private val NotificationGold = Color(0xFFD9B76E)
private val NotificationPurple = Color(0xFFAD68FF)
private val NotificationText = Color(0xFFF5F0FA)
private val NotificationMuted = Color(0xFFAAA0B9)

@Composable
fun NotificationsScreen(
    padding: PaddingValues,
    onBack: () -> Unit
) {
    var selectedFilter by remember {
        mutableStateOf("All")
    }

    val filters = listOf("All", "Unread", "Updates", "System")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotificationBackground)
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
                    .background(NotificationSurface, CircleShape)
                    .border(
                        1.dp,
                        Color(0xFF49345F),
                        CircleShape
                    )
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "‹",
                    color = NotificationText,
                    fontSize = 27.sp
                )
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "SYSTEM MESSAGES",
                    color = NotificationGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )

                Text(
                    text = "Notifications",
                    color = NotificationText,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        Color(0xFF281B3B),
                        CircleShape
                    )
                    .border(
                        1.dp,
                        NotificationPurple.copy(alpha = 0.5f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "♢",
                    color = NotificationPurple,
                    fontSize = 23.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // NOTIFICATION SUMMARY
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    NotificationSurface,
                    RoundedCornerShape(15.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(15.dp)
                )
                .padding(vertical = 17.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NotificationStat("00", "TOTAL")
            NotificationStat("00", "UNREAD")
            NotificationStat("00", "UPDATES")
        }

        Spacer(modifier = Modifier.height(26.dp))

        // FILTERS
        Text(
            text = "NOTIFICATION FILTERS",
            color = NotificationGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            filters.forEach { filter ->
                val isSelected = selectedFilter == filter

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (isSelected) Color(0xFF713BB0)
                            else Color(0xFF1A1422),
                            RoundedCornerShape(20.dp)
                        )
                        .border(
                            1.dp,
                            if (isSelected) NotificationPurple
                            else Color(0xFF30243D),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            selectedFilter = filter
                        }
                        .padding(
                            horizontal = 3.dp,
                            vertical = 11.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = filter,
                        color = if (isSelected) Color.White
                        else NotificationMuted,
                        fontSize = 9.sp,
                        fontWeight = if (isSelected) FontWeight.Bold
                        else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // NOTIFICATION LIST
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ALL NOTIFICATIONS",
                color = NotificationGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Text(
                text = "LATEST FIRST",
                color = NotificationMuted,
                fontSize = 8.sp,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // EMPTY STATE
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    NotificationSurface,
                    RoundedCornerShape(17.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(17.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 30.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .background(
                        Color(0xFF2A1B3B),
                        CircleShape
                    )
                    .border(
                        1.dp,
                        NotificationPurple.copy(alpha = 0.5f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "♢",
                    color = NotificationPurple,
                    fontSize = 34.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (selectedFilter) {
                    "Unread" -> "No Unread Notifications"
                    "Updates" -> "No Updates Yet"
                    "System" -> "No System Alerts"
                    else -> "You're All Caught Up"
                },
                color = NotificationText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "There are no notifications to display.",
                color = NotificationMuted,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "New messages will appear here.",
                color = NotificationMuted,
                fontSize = 9.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // INFORMATION CARD
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF17121D),
                    RoundedCornerShape(14.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(14.dp)
                )
                .padding(15.dp)
        ) {
            Text(
                text = "NOTIFICATION CENTER",
                color = NotificationGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "Application updates, important system messages and future game-module notifications will be collected here.",
                color = NotificationMuted,
                fontSize = 10.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun NotificationStat(
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = NotificationText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = label,
            color = NotificationMuted,
            fontSize = 8.sp,
            letterSpacing = 0.7.sp
        )
    }
}
