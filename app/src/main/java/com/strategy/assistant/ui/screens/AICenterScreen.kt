
package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import com.strategy.assistant.ui.design.AIOrbArtwork
import com.strategy.assistant.ui.localization.appText

private val VoidBlack = Color(0xFF06050A)
private val PanelBlack = Color(0xFF0D0B12)
private val RoyalGold = Color(0xFFD6B56D)
private val NeonPurple = Color(0xFF9A72FF)
private val QuietText = Color(0xFF82798E)
private val FineLine = Color(0xFF292332)

@Composable
fun AICenterScreen(onOpenChat: () -> Unit = {}, onOpenProviders: () -> Unit = {}) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(VoidBlack)
    ) {
        val density = LocalDensity.current
        val orbSize = 112.dp
        val maxX = with(density) { (maxWidth - orbSize).toPx() }.coerceAtLeast(0f)
        val maxY = with(density) { (maxHeight - orbSize).toPx() }.coerceAtLeast(0f)
        var orbX by remember(maxX) { mutableStateOf(maxX * 0.5f) }
        var orbY by remember(maxY) { mutableStateOf(with(density) { 150.dp.toPx() }.coerceAtMost(maxY)) }
        var orbAwake by remember { mutableStateOf(false) }

        // Ambient energy field spans the entire screen, not a boxed hero panel.
        val fieldMotion = rememberInfiniteTransition(label = "ai_field_motion")
        val fieldPhase by fieldMotion.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(9000), RepeatMode.Reverse),
            label = "field_phase"
        )
        val lightningPulse by fieldMotion.animateFloat(
            initialValue = 0.04f,
            targetValue = 0.20f,
            animationSpec = infiniteRepeatable(tween(1700), RepeatMode.Reverse),
            label = "lightning_pulse"
        )
        val orbDriftX by fieldMotion.animateFloat(
            initialValue = -9.dp.value,
            targetValue = 9.dp.value,
            animationSpec = infiniteRepeatable(tween(5200), RepeatMode.Reverse),
            label = "orb_drift_x"
        )
        val orbDriftY by fieldMotion.animateFloat(
            initialValue = 7.dp.value,
            targetValue = -7.dp.value,
            animationSpec = infiniteRepeatable(tween(4100), RepeatMode.Reverse),
            label = "orb_drift_y"
        )
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Broad, low-contrast energy currents behind all content.
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(Color(0xFF7041C8).copy(alpha = 0.12f), Color.Transparent),
                    center = Offset(w * (0.18f + fieldPhase * 0.58f), h * 0.36f),
                    radius = w * 0.72f
                ),
                radius = w * 0.72f,
                center = Offset(w * (0.18f + fieldPhase * 0.58f), h * 0.36f)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(Color(0xFFD6B56D).copy(alpha = 0.045f), Color.Transparent),
                    center = Offset(w * (0.82f - fieldPhase * 0.42f), h * 0.76f),
                    radius = w * 0.62f
                ),
                radius = w * 0.62f,
                center = Offset(w * (0.82f - fieldPhase * 0.42f), h * 0.76f)
            )
            // Fine lightning filaments: intentionally subtle so text remains readable.
            val bolt = Path().apply {
                moveTo(w * 0.06f, h * (0.20f + fieldPhase * 0.04f))
                lineTo(w * 0.20f, h * 0.24f)
                lineTo(w * 0.16f, h * 0.31f)
                lineTo(w * 0.38f, h * 0.35f)
                lineTo(w * 0.34f, h * 0.42f)
                lineTo(w * 0.57f, h * 0.46f)
            }
            drawPath(
                path = bolt,
                color = NeonPurple.copy(alpha = lightningPulse * 0.55f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.4.dp.toPx())
            )
            val goldBolt = Path().apply {
                moveTo(w * 0.94f, h * 0.54f)
                lineTo(w * 0.78f, h * 0.59f)
                lineTo(w * 0.84f, h * 0.65f)
                lineTo(w * 0.60f, h * 0.72f)
                lineTo(w * 0.65f, h * 0.78f)
                lineTo(w * 0.42f, h * 0.84f)
            }
            drawPath(
                path = goldBolt,
                color = RoyalGold.copy(alpha = lightningPulse * 0.42f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.1.dp.toPx())
            )
            repeat(24) { i ->
                val px = ((i * 0.173f + fieldPhase * (if (i % 2 == 0) 0.16f else -0.12f)) % 1f) * w
                val py = ((i * 0.317f + fieldPhase * 0.11f) % 1f) * h
                drawCircle(
                    color = if (i % 3 == 0) RoyalGold.copy(alpha = 0.28f) else NeonPurple.copy(alpha = 0.22f),
                    radius = if (i % 4 == 0) 2.dp.toPx() else 1.dp.toPx(),
                    center = Offset(px, py)
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                        text = appText("nexus_division"),
                        color = RoyalGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = appText("command_center"),
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = appText("system"),
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
                                    Color(0xFF82798E),
                                    CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = appText("coming_soon"),
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

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onOpenChat,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = RoyalGold, contentColor = Color(0xFF17111F))
            ) { Text("OPEN INTELLIGENCE CHANNEL", fontSize = 9.sp, fontWeight = FontWeight.Bold) }
            Button(
                onClick = onOpenProviders,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF241B32), contentColor = RoyalGold)
            ) { Text("AI SETUP", fontSize = 9.sp) }
        }

        // Keep the heading copy in the normal flow. The orb itself lives in
        // the full-screen energy field above, so it is not boxed into this hero.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = appText("central_core"),
                color = QuietText,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.3.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text("ARTIFICIAL INTELLIGENCE", color = RoyalGold, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(appText("awaiting_command"), color = QuietText, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(7.dp))
            Text("TOUCH TO AWAKEN  •  DRAG TO ORBIT", color = QuietText.copy(alpha = 0.78f), fontSize = 7.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.15.sp)
            Spacer(modifier = Modifier.height(7.dp))
            Text(appText("strategic_advantage"), color = RoyalGold.copy(alpha = 0.85f), fontSize = 8.sp, letterSpacing = 2.sp)
            Spacer(modifier = Modifier.height(16.dp))
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
                    label = appText("ai_core"),
                    value = appText("coming_soon"),
                    color = Color(0xFF75D994)
                )

                VerticalDivider()

                TelemetryItem(
                    label = appText("operations"),
                    value = "03",
                    color = RoyalGold
                )

                VerticalDivider()

                TelemetryItem(
                    label = appText("mode"),
                    value = appText("coming_soon"),
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
                        text = appText("tactical_modules"),
                        color = RoyalGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = appText("operations"),
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = appText("three_modules"),
                    color = QuietText,
                    fontSize = 8.sp,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            TacticalOperation(
                number = "01",
                title = appText("strategic_analysis"),
                subtitle = appText("analyze_battlefield"),
                symbol = "⌕",
                tag = appText("coming_soon")
            )

            TacticalOperation(
                number = "02",
                title = appText("strategy_planning"),
                subtitle = appText("develop_next_move"),
                symbol = "◈",
                tag = appText("coming_soon")
            )

            TacticalOperation(
                number = "03",
                title = appText("performance_review"),
                subtitle = appText("evaluate_progress"),
                symbol = "↗",
                tag = appText("coming_soon")
            )

            Spacer(modifier = Modifier.height(29.dp))

            // AI CHANNEL
            Text(
                text = appText("secure_communication"),
                color = RoyalGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appText("intelligence_channel"),
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
                            text = appText("personal_assistant"),
                            color = QuietText,
                            fontSize = 9.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = appText("coming_soon"),
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
                    text = appText("welcome_commander"),
                    color = RoyalGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = appText("core_standing_by"),
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
                        text = appText("conversation_coming_soon"),
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

            AIOrbArtwork(
                modifier = Modifier
                    .offset { IntOffset((orbX + with(density) { orbDriftX.dp.toPx() }).roundToInt(), (orbY + with(density) { orbDriftY.dp.toPx() }).roundToInt()) }
                    .pointerInput(maxX, maxY) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            orbX = (orbX + dragAmount.x).coerceIn(0f, maxX)
                            orbY = (orbY + dragAmount.y).coerceIn(0f, maxY)
                        }
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { orbAwake = !orbAwake },
                            onDoubleTap = { /* No zoom or scale gesture. */ }
                        )
                    },
                orbSize = orbSize,
                active = orbAwake
            )
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

        }
    }
}
