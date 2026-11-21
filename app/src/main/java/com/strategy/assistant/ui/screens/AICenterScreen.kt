
package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.strategy.assistant.ui.design.AIOrb

private val VoidBlack = Color(0xFF06050A)
private val PanelBlack = Color(0xFF0D0B12)
private val RoyalGold = Color(0xFFD6B56D)
private val NeonPurple = Color(0xFF9A72FF)
private val QuietText = Color(0xFF82798E)
private val FineLine = Color(0xFF292332)

@Composable
fun AICenterScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VoidBlack)
            .verticalScroll(rememberScrollState())
    ) {

        // COMMAND HEADER
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "NEXUS INTELLIGENCE DIVISION",
                        color = RoyalGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Command Center",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "SYSTEM",
                        color = QuietText,
                        fontSize = 8.sp,
                        letterSpacing = 1.5.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    Color(0xFF4ADE80),
                                    CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "ONLINE",
                            color = Color(0xFF8BD6A0),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(17.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                RoyalGold.copy(alpha = 0.8f),
                                NeonPurple.copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        // CENTRAL AI CORE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF211434),
                            Color(0xFF100B19),
                            VoidBlack
                        ),
                        radius = 550f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CENTRAL INTELLIGENCE CORE",
                    color = QuietText,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.3.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                AIOrb(
                    orbSize = 225.dp,
                    status = "AWAITING COMMAND"
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "YOUR STRATEGIC ADVANTAGE",
                    color = RoyalGold.copy(alpha = 0.85f),
                    fontSize = 8.sp,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(21.dp))
            }
        }

        // SYSTEM TELEMETRY
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(13.dp))
                    .background(PanelBlack)
                    .border(
                        1.dp,
                        FineLine,
                        RoundedCornerShape(13.dp)
                    )
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TelemetryItem(
                    label = "AI CORE",
                    value = "READY",
                    color = Color(0xFF75D994)
                )

                VerticalDivider()

                TelemetryItem(
                    label = "OPERATIONS",
                    value = "03",
                    color = RoyalGold
                )

                VerticalDivider()

                TelemetryItem(
                    label = "MODE",
                    value = "LOCAL",
                    color = NeonPurple
                )
            }

            Spacer(modifier = Modifier.height(29.dp))

            // OPERATIONS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "TACTICAL MODULES",
                        color = RoyalGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Operations",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "03 MODULES",
                    color = QuietText,
                    fontSize = 8.sp,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            TacticalOperation(
                number = "01",
                title = "Strategic Analysis",
                subtitle = "Analyze the battlefield",
                symbol = "⌕",
                tag = "ANALYSIS"
            )

            TacticalOperation(
                number = "02",
                title = "Strategy Planning",
                subtitle = "Develop your next move",
                symbol = "◈",
                tag = "PLANNING"
            )

            TacticalOperation(
                number = "03",
                title = "Performance Review",
                subtitle = "Evaluate your progress",
                symbol = "↗",
                tag = "REVIEW"
            )

            Spacer(modifier = Modifier.height(29.dp))

            // AI CHANNEL
            Text(
                text = "SECURE COMMUNICATION",
                color = RoyalGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Intelligence Channel",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF171222),
                                Color(0xFF0C0A11)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        FineLine,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(17.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2A1B3C))
                            .border(
                                1.dp,
                                RoyalGold.copy(alpha = 0.35f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✧",
                            color = RoyalGold,
                            fontSize = 19.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(11.dp))

                    Column {
                        Text(
                            text = "STRATEGY AI",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = "Personal intelligence assistant",
                            color = QuietText,
                            fontSize = 9.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "ENCRYPTED",
                        color = Color(0xFF75D994),
                        fontSize = 8.sp,
                        letterSpacing = 0.8.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(FineLine)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Welcome, Commander.",
                    color = RoyalGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "The intelligence core is standing by. Select a tactical module to begin your next operation.",
                    color = Color(0xFFB4ADBE),
                    fontSize = 11.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(17.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF08070C))
                        .border(
                            1.dp,
                            FineLine,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "✧",
                        color = NeonPurple,
                        fontSize = 17.sp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "AI conversation interface coming soon",
                        color = QuietText,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "STRATEGY ASSISTANT  •  COMMAND DIVISION",
                color = Color(0xFF51495C),
                fontSize = 8.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
private fun TelemetryItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = QuietText,
            fontSize = 8.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(29.dp)
            .background(FineLine)
    )
}

@Composable
private fun TacticalOperation(
    number: String,
    title: String,
    subtitle: String,
    symbol: String,
    tag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF17131E),
                        Color(0xFF0D0B12)
                    )
                )
            )
            .border(
                1.dp,
                FineLine,
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 13.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = number,
            color = RoyalGold,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(13.dp))

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF20182C))
                .border(
                    1.dp,
                    NeonPurple.copy(alpha = 0.22f),
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol,
                color = NeonPurple,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = Color(0xFFF2EEF7),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                color = QuietText,
                fontSize = 10.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = tag,
                color = RoyalGold.copy(alpha = 0.8f),
                fontSize = 7.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.7.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "›",
                color = RoyalGold,
                fontSize = 20.sp
            )
        }
    }
}
