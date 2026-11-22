package com.strategy.assistant.ui.design

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.R
import kotlin.math.min

@Composable
fun AIOrbArtwork(
    modifier: Modifier = Modifier,
    orbSize: Dp = 220.dp,
    active: Boolean = false
) {
    val transition = rememberInfiniteTransition(label = "Orb orbit animation")
    val ringAngle by transition.animateFloat(
        initialValue = -18f,
        targetValue = 342f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (active) 6500 else 18000
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Independent orbital ring"
    )
    val energyPulse by transition.animateFloat(
        initialValue = 0.96f,
        targetValue = if (active) 1.045f else 1.018f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (active) 900 else 2600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Core energy pulse"
    )
    val glow by transition.animateFloat(
        initialValue = 0.76f,
        targetValue = if (active) 1f else 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (active) 850 else 2400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Core glow"
    )

    Box(
        modifier = modifier.size(orbSize),
        contentAlignment = Alignment.Center
    ) {
        // Keep the supplied core artwork stationary: only the separate orbit graphic moves.
        Image(
            painter = painterResource(
                id = if (active) R.drawable.ai_orb_active else R.drawable.ai_orb_idle
            ),
            contentDescription = "Strategy Assistant AI core",
            modifier = Modifier
                .fillMaxSize(0.88f)
                .alpha(glow)
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val side = min(size.width, size.height)
            val ringWidth = side * 0.91f
            val ringHeight = side * 0.34f
            rotate(degrees = ringAngle, pivot = center) {
                drawOval(
                    color = Color(0xFFDBA9FF).copy(alpha = 0.82f),
                    topLeft = Offset(
                        center.x - ringWidth / 2f,
                        center.y - ringHeight / 2f
                    ),
                    size = Size(ringWidth, ringHeight),
                    style = Stroke(width = side * 0.018f)
                )
                drawOval(
                    color = Color(0xFFFFD98A).copy(alpha = 0.48f),
                    topLeft = Offset(
                        center.x - ringWidth * 0.47f,
                        center.y - ringHeight * 0.43f
                    ),
                    size = Size(ringWidth * 0.94f, ringHeight * 0.86f),
                    style = Stroke(width = side * 0.006f)
                )
            }
            // Small moving energy point makes the ring's independent motion easier to see.
            val radians = Math.toRadians(ringAngle.toDouble())
            val orbitX = side * 0.455f
            val orbitY = side * 0.17f
            val dot = Offset(
                center.x + (kotlin.math.cos(radians) * orbitX).toFloat(),
                center.y + (kotlin.math.sin(radians) * orbitY).toFloat()
            )
            drawCircle(
                color = Color(0xFFFFD98A).copy(alpha = 0.95f),
                radius = side * 0.027f,
                center = dot
            )
        }
    }
}

@Composable
fun AIOrb(
    modifier: Modifier = Modifier,
    orbSize: Dp = 220.dp,
    status: String = "SYSTEM READY",
    active: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AIOrbArtwork(
            orbSize = orbSize,
            active = active
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "ARTIFICIAL INTELLIGENCE",
            color = StrategyColors.Gold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.5.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = status,
            color = StrategyColors.TextSecondary,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp
        )
    }
}
