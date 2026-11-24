package com.strategy.assistant.ui.design

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sceneview.SceneView
import io.github.sceneview.SurfaceType
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelInstance
import io.github.sceneview.rememberModelLoader

/**
 * Interactive 3D Royal AI Orb.
 *
 * The transparent Filament surface lets the model blend into the app background.
 * Camera gestures are intentionally disabled; screen-level gestures control movement.
 */
@Composable
fun RoyalOrb3DView(
    modifier: Modifier = Modifier,
    orbSize: Dp = 225.dp
) {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val idleTransition = rememberInfiniteTransition(label = "orb_idle")
    val idleScale = idleTransition.animateFloat(
        initialValue = 0.985f,
        targetValue = 1.015f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb_breathing"
    )

    Box(
        modifier = modifier
            .size(orbSize)
            .graphicsLayer {
                scaleX = idleScale.value
                scaleY = idleScale.value
            }
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF8E55E8).copy(alpha = 0.22f),
                            Color(0xFF49246F).copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    ),
                    radius = size.minDimension * 0.49f
                )
            },
        contentAlignment = Alignment.Center
    ) {
        SceneView(
            modifier = Modifier.fillMaxSize(),
            surfaceType = SurfaceType.TextureSurface,
            engine = engine,
            modelLoader = modelLoader,
            isOpaque = false,
            autoFitContent = true,
            cameraManipulator = null,
        ) {
            rememberModelInstance(
                modelLoader,
                "StrategyAssistant_RoyalOrb_V3.glb"
            )?.let { instance ->
                ModelNode(
                    modelInstance = instance,
                    scaleToUnits = 1.8f,
                    autoAnimate = true,
                    animationLoop = true
                )
            }
        }
    }
}

/**
 * Compact artwork variant used on Home. It deliberately has no square frame:
 * the orb floats directly on the surrounding surface.
 */
@Composable
fun AIOrbArtwork(
    modifier: Modifier = Modifier,
    orbSize: Dp = 220.dp,
    active: Boolean = false,
    onOrbTap: () -> Unit = {}
) {
    val glowAlpha = rememberInfiniteTransition(label = "orb_glow").animateFloat(
        initialValue = if (active) 0.20f else 0.10f,
        targetValue = if (active) 0.30f else 0.17f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb_glow_pulse"
    )

    Box(
        modifier = modifier.size(orbSize),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFB68CFF).copy(alpha = glowAlpha.value),
                                Color(0xFF8D63E8).copy(alpha = glowAlpha.value * 0.45f),
                                Color.Transparent
                            )
                        ),
                        radius = size.minDimension * 0.5f
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            RoyalOrb3DView(
                modifier = Modifier,
                orbSize = orbSize
            )
        }
    }
}

@Composable
fun AIOrb(
    modifier: Modifier = Modifier,
    orbSize: Dp = 260.dp,
    status: String = "SYSTEM READY",
    active: Boolean = false,
    onOrbTap: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AIOrbArtwork(
            orbSize = orbSize,
            active = active,
            onOrbTap = onOrbTap
        )

        Spacer(modifier = Modifier.height(7.dp))

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
