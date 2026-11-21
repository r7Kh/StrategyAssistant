
package com.strategy.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.ui.screens.AICenterScreen
import com.strategy.assistant.ui.theme.StrategyAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StrategyAssistantTheme {
                StrategyAssistantApp()
            }
        }
    }
}

private val AppBackground = Color(0xFF09090F)
private val CardBackground = Color(0xFF151321)
private val PurpleAccent = Color(0xFF8B5CF6)
private val GoldAccent = Color(0xFFD6B56D)
private val MainText = Color(0xFFEAE7F2)
private val SecondaryText = Color(0xFFAAA3BA)

@Composable
fun StrategyAssistantApp() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF100E19),
                contentColor = MainText
            ) {
                val items = listOf(
                    Triple("⌂", "Home", 0),
                    Triple("✧", "AI Center", 1),
                    Triple("▤", "Library", 2),
                    Triple("☰", "More", 3)
                )

                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item.third,
                        onClick = { selectedTab = item.third },
                        icon = {
                            Text(
                                text = item.first,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        label = {
                            Text(
                                text = item.second,
                                fontSize = 10.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = GoldAccent,
                            selectedTextColor = GoldAccent,
                            unselectedIconColor = SecondaryText,
                            unselectedTextColor = SecondaryText,
                            indicatorColor = Color(0xFF29213B)
                        )
                    )
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> HomeScreen(padding)

            1 -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AICenterScreen()
            }

            2 -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                PlaceholderScreen(
                    "Game Library",
                    "Manage your game modules"
                )
            }

            3 -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                PlaceholderScreen(
                    "More",
                    "Settings and application tools"
                )
            }
        }
    }
}

@Composable
fun HomeScreen(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(padding)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "STRATEGY",
                    color = MainText,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "ASSISTANT",
                    color = GoldAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .border(
                        1.dp,
                        GoldAccent.copy(alpha = 0.5f),
                        CircleShape
                    )
                    .background(CardBackground, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "♛",
                    fontSize = 24.sp,
                    color = GoldAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "WELCOME BACK",
            color = SecondaryText,
            fontSize = 11.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Ready to lead?",
            color = MainText,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(22.dp))

        AiStatusCard()

        Spacer(modifier = Modifier.height(26.dp))

        SectionTitle("QUICK ACCESS", "Explore")

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickAccessCard(
                modifier = Modifier.weight(1f),
                symbol = "✧",
                title = "AI Center",
                description = "Talk with your AI",
                accent = PurpleAccent
            )

            QuickAccessCard(
                modifier = Modifier.weight(1f),
                symbol = "▤",
                title = "Game Library",
                description = "Your game modules",
                accent = GoldAccent
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickAccessCard(
                modifier = Modifier.weight(1f),
                symbol = "◷",
                title = "Activity",
                description = "Recent operations",
                accent = GoldAccent
            )

            QuickAccessCard(
                modifier = Modifier.weight(1f),
                symbol = "⚙",
                title = "Management",
                description = "Manage your setup",
                accent = PurpleAccent
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        SectionTitle("SYSTEM OVERVIEW", "Live status")

        Spacer(modifier = Modifier.height(14.dp))

        SystemOverviewCard()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "STRATEGY ASSISTANT  •  VERSION 1.0",
            color = SecondaryText.copy(alpha = 0.55f),
            fontSize = 9.sp,
            letterSpacing = 1.2.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AiStatusCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                PurpleAccent.copy(alpha = 0.4f),
                RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF191426)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(66.dp)
                    .border(1.dp, PurpleAccent, CircleShape)
                    .background(Color(0xFF241B3B), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✧",
                    color = GoldAccent,
                    fontSize = 36.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "AI CORE",
                    color = SecondaryText,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "System Ready",
                    color = MainText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(Color(0xFF4ADE80), CircleShape)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Waiting for command",
                        color = SecondaryText,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, action: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MainText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Text(
            text = action,
            color = GoldAccent,
            fontSize = 11.sp
        )
    }
}

@Composable
fun QuickAccessCard(
    modifier: Modifier = Modifier,
    symbol: String,
    title: String,
    description: String,
    accent: Color
) {
    Card(
        modifier = modifier.height(112.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = symbol,
                color = accent,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Column {
                Text(
                    text = title,
                    color = MainText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    color = SecondaryText,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun SystemOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OverviewRow("AI Engine", "Ready", Color(0xFF4ADE80))
            Spacer(modifier = Modifier.height(14.dp))
            OverviewRow("Game Modules", "Not configured", GoldAccent)
            Spacer(modifier = Modifier.height(14.dp))
            OverviewRow("Storage", "Local mode", PurpleAccent)
        }
    }
}

@Composable
fun OverviewRow(title: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = SecondaryText,
            fontSize = 12.sp
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(color, CircleShape)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = value,
                color = MainText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PlaceholderScreen(title: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "✧",
                color = GoldAccent,
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                color = MainText,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                color = SecondaryText,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "UNDER DEVELOPMENT",
                color = GoldAccent,
                fontSize = 10.sp,
                letterSpacing = 2.sp
            )
        }
    }
}
