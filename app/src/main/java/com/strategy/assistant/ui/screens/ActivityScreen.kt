
package com.strategy.assistant.ui.screens

import com.strategy.assistant.ui.localization.appText

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

private val ActivityBackground = Color(0xFF090710)
private val ActivitySurface = Color(0xFF171220)
private val ActivityGold = Color(0xFFD9B76E)
private val ActivityPurple = Color(0xFFAD68FF)
private val ActivityText = Color(0xFFF5F0FA)
private val ActivityMuted = Color(0xFFAAA0B9)

@Composable
fun ActivityScreen(
    padding: PaddingValues,
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val filters = listOf("All", "AI", "Games", "Accounts", "System")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ActivityBackground)
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
                    .size(39.dp)
                    .background(ActivitySurface, CircleShape)
                    .border(1.dp, Color(0xFF49345F), CircleShape)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text("‹", color = ActivityText, fontSize = 27.sp)
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(appText("system_records"),
                    color = ActivityGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )
                Text(appText("activity_center"),
                    color = ActivityText,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                "◷",
                color = ActivityPurple,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // STATISTICS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    ActivitySurface,
                    RoundedCornerShape(15.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(15.dp)
                )
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActivityStat("00", "TOTAL")
            ActivityStat("00", "AI")
            ActivityStat("00", "GAMES")
            ActivityStat("00", "SYSTEM")
        }

        Spacer(modifier = Modifier.height(26.dp))

        // FILTERS
        Text(appText("activity_filters"),
            color = ActivityGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
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
                            if (isSelected) ActivityPurple
                            else Color(0xFF30243D),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { selectedFilter = filter }
                        .padding(
                            horizontal = 3.dp,
                            vertical = 10.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = appText("filter_${filter.lowercase()}"),
                        color = if (isSelected) Color.White else ActivityMuted,
                        fontSize = 9.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(27.dp))

        // HISTORY
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(appText("activity_history"),
                color = ActivityGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Text(appText("latest_first"),
                color = ActivityMuted,
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
                    ActivitySurface,
                    RoundedCornerShape(17.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(17.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 29.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .background(
                        Color(0xFF2A1B3B),
                        CircleShape
                    )
                    .border(
                        1.dp,
                        ActivityPurple.copy(alpha = 0.45f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "◷",
                    color = ActivityPurple,
                    fontSize = 31.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(appText("no_activity"),
                color = ActivityText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(appText("activity_history_appear"),
                color = ActivityMuted,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(appText("activity_events_recorded"),
                color = ActivityMuted,
                fontSize = 9.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PRIVACY NOTE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF17121D),
                    RoundedCornerShape(13.dp)
                )
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                "◆",
                color = ActivityGold,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(appText("activity_privacy"),
                    color = ActivityGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(appText("activity_privacy_note"),
                    color = ActivityMuted,
                    fontSize = 9.sp,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun ActivityStat(
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            color = ActivityText,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            label,
            color = ActivityMuted,
            fontSize = 8.sp,
            letterSpacing = 0.6.sp
        )
    }
}
