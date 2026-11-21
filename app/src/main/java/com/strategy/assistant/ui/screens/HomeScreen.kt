
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HomeBackground = Color(0xFF09090F)
private val HomePurple = Color(0xFF9B55F5)
private val HomeGold = Color(0xFFD9B76E)
private val HomeText = Color(0xFFF5F0FA)
private val HomeMuted = Color(0xFFAAA0B9)

@Composable
fun PremiumHomeScreen(
    padding: PaddingValues,
    onOpenAi: () -> Unit,
    onOpenLibrary: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 19.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // BRAND HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF52248A), Color(0xFF170D2A))
                        )
                    )
                    .border(
                        1.dp,
                        HomeGold.copy(alpha = 0.65f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("♛", color = HomeGold, fontSize = 27.sp)
            }

            Spacer(modifier = Modifier.width(11.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "STRATEGY",
                    color = HomeText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.7.sp
                )
                Text(
                    "ASSISTANT",
                    color = HomeGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
            }

            Text("♙", color = HomePurple, fontSize = 23.sp)
        }

        Spacer(modifier = Modifier.height(23.dp))

        // WELCOME BANNER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(17.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF28143F),
                            Color(0xFF171025),
                            Color(0xFF11101B)
                        )
                    )
                )
                .border(
                    1.dp,
                    Color(0xFF58347B).copy(alpha = 0.65f),
                    RoundedCornerShape(17.dp)
                )
                .padding(17.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "WELCOME BACK",
                        color = HomeGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.8.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        "Ahmad",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        "Your command center is ready.",
                        color = HomeMuted,
                        fontSize = 10.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(69.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF31194F)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("◈", color = HomePurple, fontSize = 43.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(17.dp))

        // AI ASSISTANT FEATURE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(17.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF211631), Color(0xFF110D1A))
                    )
                )
                .border(
                    1.dp,
                    Color(0xFF69409A).copy(alpha = 0.6f),
                    RoundedCornerShape(17.dp)
                )
                .clickable { onOpenAi() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF301D4C))
                    .border(
                        1.dp,
                        HomePurple.copy(alpha = 0.5f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("⬡", color = HomePurple, fontSize = 39.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "AI ASSISTANT",
                    color = HomeText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    "Get game strategies, analysis\nand smart plans.",
                    color = HomeMuted,
                    fontSize = 10.sp,
                    lineHeight = 16.sp
                )
            }

            Text("›", color = HomeGold, fontSize = 27.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ACCOUNT STATISTICS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF120F1B))
                .border(
                    1.dp,
                    Color(0xFF30243F),
                    RoundedCornerShape(15.dp)
                )
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeStatistic("0", "GAMES", "🎮")
            StatisticDivider()
            HomeStatistic("0", "ACCOUNTS", "♟")
            StatisticDivider()
            HomeStatistic("0", "ACTIVITIES", "◷")
        }

        Spacer(modifier = Modifier.height(23.dp))

        // QUICK ACCESS
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "QUICK ACCESS",
                color = HomeText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Text("Explore", color = HomeGold, fontSize = 10.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            QuickHomeItem(
                modifier = Modifier.weight(1f),
                symbol = "✧",
                title = "AI Center",
                onClick = onOpenAi
            )

            QuickHomeItem(
                modifier = Modifier.weight(1f),
                symbol = "▣",
                title = "Game Library",
                onClick = onOpenLibrary
            )
        }

        Spacer(modifier = Modifier.height(11.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            QuickHomeItem(
                modifier = Modifier.weight(1f),
                symbol = "◷",
                title = "Activity",
                onClick = {}
            )

            QuickHomeItem(
                modifier = Modifier.weight(1f),
                symbol = "⚙",
                title = "Management",
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(23.dp))

        Text(
            "RECENT ACTIVITY",
            color = HomeText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        RecentActivityItem(
            symbol = "✧",
            title = "AI Assistant",
            description = "Intelligence core is ready",
            time = "NOW"
        )

        RecentActivityItem(
            symbol = "▣",
            title = "Game Library",
            description = "No games added yet",
            time = "NEW"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "STRATEGY ASSISTANT  •  VERSION 1.0",
            color = Color(0xFF62596F),
            fontSize = 8.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun HomeStatistic(
    value: String,
    label: String,
    symbol: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(symbol, color = HomePurple, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            value,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            label,
            color = HomeMuted,
            fontSize = 8.sp,
            letterSpacing = 0.6.sp
        )
    }
}

@Composable
private fun StatisticDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(48.dp)
            .background(Color(0xFF33273F))
    )
}

@Composable
private fun QuickHomeItem(
    modifier: Modifier = Modifier,
    symbol: String,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF28173A), Color(0xFF171020))
                )
            )
            .border(
                1.dp,
                Color(0xFF49315F),
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(symbol, color = HomePurple, fontSize = 20.sp)

        Spacer(modifier = Modifier.width(9.dp))

        Text(
            title,
            color = HomeText,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun RecentActivityItem(
    symbol: String,
    title: String,
    description: String,
    time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(Color(0xFF120F1A))
            .border(
                1.dp,
                Color(0xFF282131),
                RoundedCornerShape(11.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF291B3D)),
            contentAlignment = Alignment.Center
        ) {
            Text(symbol, color = HomePurple, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.width(11.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                color = HomeText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                description,
                color = HomeMuted,
                fontSize = 9.sp
            )
        }

        Text(
            time,
            color = HomeGold,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
