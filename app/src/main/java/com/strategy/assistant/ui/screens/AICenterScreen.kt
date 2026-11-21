
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
import com.strategy.assistant.ui.design.StrategyColors

private val DeepBlack = Color(0xFF07060B)
private val PanelDark = Color(0xFF100D18)
private val PurpleGlow = Color(0xFF542A91)
private val GoldMetal = Color(0xFFD6B56D)

@Composable
fun AICenterScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack)
            .verticalScroll(rememberScrollState())
    ) {

        // Background atmosphere
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF171025),
                            Color(0xFF0D0A14),
                            DeepBlack
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {

                Spacer(modifier = Modifier.height(25.dp))

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            text = "STRATEGIC INTELLIGENCE",
                            color = GoldMetal,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.5.sp
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = "AI Command",
                            color = Color.White,
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Your strategic intelligence center",
                            color = Color(0xFF92899F),
                            fontSize = 11.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(43.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF21182F))
                            .border(
                                1.dp,
                                GoldMetal.copy(alpha = 0.5f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "♛",
                            color = GoldMetal,
                            fontSize = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Fine gold separator
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    GoldMetal.copy(alpha = 0.6f),
                                    PurpleGlow.copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Orb area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AIOrb(
                        orbSize = 250.dp,
                        status = "INTELLIGENCE STANDBY"
                    )
                }

                // Online indicator
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                        .background(Color(0xFF111A17))
                        .border(
                            1.dp,
                            Color(0x334ADE80),
                            CircleShape
                        )
                        .padding(
                            horizontal = 15.dp,
                            vertical = 7.dp
                        ),
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

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "CORE SYSTEM ONLINE",
                        color = Color(0xFF91DCA8),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        ) {

            // Operations heading
            SectionHeading(
                eyebrow = "INTELLIGENCE TOOLS",
                title = "Strategic Operations"
            )

            Spacer(modifier = Modifier.height(17.dp))

            // Operation list
            OperationRow(
                number = "01",
                title = "Strategic Analysis",
                description = "Understand your current situation",
                symbol = "⌕"
            )

            OperationRow(
                number = "02",
                title = "Strategy Planning",
                description = "Build a plan around your objectives",
                symbol = "◈"
            )

            OperationRow(
                number = "03",
                title = "Performance Review",
                description = "Discover weaknesses and improvements",
                symbol = "↗"
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Intelligence channel
            SectionHeading(
                eyebrow = "PRIVATE AI CHANNEL",
                title = "Intelligence Channel"
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Chat panel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(19.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF171222),
                                Color(0xFF0E0B14)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Color(0xFF30263E),
                        RoundedCornerShape(19.dp)
                    )
                    .padding(17.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(29.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF302047)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✧",
                            color = GoldMetal,
                            fontSize = 17.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

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
                            color = Color(0xFF82798F),
                            fontSize = 9.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                Text(
                    text = "Welcome, Commander.",
                    color = GoldMetal,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Your intelligence center is ready. Select an operation or prepare your next strategic objective.",
                    color = Color(0xFFB8B0C5),
                    fontSize = 12.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF09070D))
                        .border(
                            1.dp,
                            Color(0xFF30263E),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Chat interface coming soon...",
                        color = Color(0xFF6F667D),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "STRATEGY ASSISTANT  /  INTELLIGENCE DIVISION",
                color = Color(0xFF51495C),
                fontSize = 8.sp,
                letterSpacing = 1.1.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
private fun SectionHeading(
    eyebrow: String,
    title: String
) {
    Column {

        Text(
            text = eyebrow,
            color = GoldMetal,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun OperationRow(
    number: String,
    title: String,
    description: String,
    symbol: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF100D17))
            .border(
                1.dp,
                Color(0xFF282132),
                RoundedCornerShape(14.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = number,
            color = GoldMetal,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(14.dp))

        Box(
            modifier = Modifier
                .size(37.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(Color(0xFF21172F)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol,
                color = Color(0xFFB99AFF),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = Color(0xFFF0ECF5),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                color = Color(0xFF898194),
                fontSize = 10.sp
            )
        }

        Text(
            text = "›",
            color = GoldMetal,
            fontSize = 24.sp
        )
    }
}
