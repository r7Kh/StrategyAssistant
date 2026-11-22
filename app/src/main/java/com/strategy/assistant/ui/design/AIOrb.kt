
package com.strategy.assistant.ui.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sceneview.SceneView
import io.github.sceneview.SurfaceType
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraManipulator
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelInstance
import io.github.sceneview.rememberModelLoader

/**
 * Interactive 3D Royal AI Orb.
 * Transparent background with automatic camera framing.
 */
@Composable
fun RoyalOrb3DView(
    modifier: Modifier = Modifier,
    orbSize: Dp = 225.dp
) {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)

    Box(
        modifier = modifier
            .size(orbSize)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF39205F).copy(alpha = 0.48f),
                        Color(0xFF171024).copy(alpha = 0.22f),
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        SceneView(
            modifier = Modifier.fillMaxSize(),
            surfaceType = SurfaceType.TextureSurface,
            engine = engine,
            modelLoader = modelLoader,
            isOpaque = false,
            autoFitContent = true,
            cameraManipulator = rememberCameraManipulator()
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

@Composable
fun AIOrbArtwork(
    modifier: Modifier = Modifier,
    orbSize: Dp = 220.dp,
    active: Boolean = false
) {
    val glowColor = if (active) {
        Color(0xFFB68CFF)
    } else {
        Color(0xFF8D63E8)
    }

    Box(
        modifier = modifier.size(orbSize),
        contentAlignment = Alignment.Center
    ) {
        RoyalOrb3DView(
            modifier = Modifier,
            orbSize = orbSize
        )

        Box(
            modifier = Modifier
                .size(orbSize * 0.96f)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            glowColor.copy(
                                alpha = if (active) 0.10f else 0.045f
                            ),
                            Color.Transparent
                        )
                    )
                )
        )
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
        RoyalOrb3DView(
            orbSize = orbSize
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
