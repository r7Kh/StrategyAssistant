package com.strategy.assistant.ui.design

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.R

@Composable
fun AIOrb(
    modifier: Modifier = Modifier,
    orbSize: Dp = 220.dp,
    status: String = "SYSTEM READY",
    active: Boolean = false
) {
    val transition = rememberInfiniteTransition(label = "AI Orb Motion")

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (active) 9000 else 26000),
            repeatMode = RepeatMode.Restart
        ),
        label = "Orb rotation"
    )

    val pulse by transition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (active) 1100 else 2800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb energy pulse"
    )

    val glowAlpha by transition.animateFloat(
        initialValue = 0.48f,
        targetValue = if (active) 0.95f else 0.72f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (active) 900 else 2200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb glow"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = if (active) R.drawable.ai_orb_active else R.drawable.ai_orb_idle
            ),
            contentDescription = "Strategy Assistant AI orb",
            modifier = Modifier
                .size(orbSize)
                .graphicsLayer {
                    rotationZ = rotation
                    scaleX = pulse
                    scaleY = pulse
                }
                .alpha(glowAlpha)
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
