
package com.strategy.assistant.ui.design

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AIOrb(
    modifier: Modifier = Modifier,
    orbSize: Dp = 220.dp,
    status: String = "SYSTEM READY"
) {
    val transition = rememberInfiniteTransition(
        label = "AI Orb"
    )

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 18000
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Orb Rotation"
    )

    val reverseRotation by transition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 24000
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Orb Reverse Rotation"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.size(orbSize),
            contentAlignment = Alignment.Center
        ) {

            // Outer orbital rings
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationZ = rotation
                    }
            ) {
                val center = Offset(
                    x = size.width / 2f,
                    y = size.height / 2f
                )

                val radius = size.minDimension * 0.43f

                // Outer energy glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x447040D8),
                            Color(0x227040D8),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radius * 1.5f
                    ),
                    radius = radius * 1.5f,
                    center = center
                )

                // Outer ring
                drawCircle(
                    color = Color(0x557040D8),
                    radius = radius,
                    center = center,
                    style = Stroke(
                        width = 2.dp.toPx()
                    )
                )

                // Gold and purple orbital arc
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFFD6B56D),
                            Color(0xFFA78BFA),
                            Color.Transparent
                        ),
                        center = center
                    ),
                    startAngle = 20f,
                    sweepAngle = 115f,
                    useCenter = false,
                    topLeft = Offset(
                        center.x - radius,
                        center.y - radius
                    ),
                    size = Size(
                        radius * 2,
                        radius * 2
                    ),
                    style = Stroke(
                        width = 3.dp.toPx()
                    )
                )

                // Inner orbital arc
                drawArc(
                    color = Color(0xFF8B5CF6),
                    startAngle = 195f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = Offset(
                        center.x - radius * 0.88f,
                        center.y - radius * 0.88f
                    ),
                    size = Size(
                        radius * 1.76f,
                        radius * 1.76f
                    ),
                    style = Stroke(
                        width = 1.5.dp.toPx()
                    )
                )

                // Orbital energy point
                val pointRadius = radius * 0.94f

                val pointX = center.x +
                        cos(Math.toRadians(45.0)).toFloat() * pointRadius

                val pointY = center.y +
                        sin(Math.toRadians(45.0)).toFloat() * pointRadius

                drawCircle(
                    color = Color(0xFFE8D19B),
                    radius = 5.dp.toPx(),
                    center = Offset(pointX, pointY)
                )

                drawCircle(
                    color = Color(0x558B5CF6),
                    radius = 9.dp.toPx(),
                    center = Offset(pointX, pointY)
                )
            }

            // Inner energy core
            Canvas(
                modifier = Modifier
                    .size(orbSize * 0.52f)
                    .graphicsLayer {
                        rotationZ = reverseRotation
                    }
            ) {
                val center = Offset(
                    size.width / 2f,
                    size.height / 2f
                )

                val radius = size.minDimension / 2f

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFB99AFF),
                            Color(0xFF7040D8),
                            Color(0xFF281348),
                            Color(0xFF100A1D)
                        ),
                        center = Offset(
                            center.x * 0.65f,
                            center.y * 0.55f
                        ),
                        radius = radius * 1.5f
                    ),
                    radius = radius,
                    center = center
                )

                drawCircle(
                    color = Color(0x99D6B56D),
                    radius = radius * 0.82f,
                    center = center,
                    style = Stroke(
                        width = 1.dp.toPx()
                    )
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE8D19B),
                            Color(0xFFD6B56D),
                            Color(0xFF8C6A32)
                        ),
                        center = center,
                        radius = radius * 0.3f
                    ),
                    radius = radius * 0.16f,
                    center = center
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "ARTIFICIAL INTELLIGENCE",
            color = StrategyColors.Gold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.5.sp
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = status,
            color = StrategyColors.TextSecondary,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp
        )
    }
}
