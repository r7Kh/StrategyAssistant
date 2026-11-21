
package com.strategy.assistant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StrategyDarkColorScheme = darkColorScheme(
    primary = BrandPurpleLight,
    onPrimary = Color.White,

    primaryContainer = BrandPurpleDark,
    onPrimaryContainer = TextPrimary,

    secondary = BrandGold,
    onSecondary = BackgroundPrimary,

    secondaryContainer = SurfaceElevated,
    onSecondaryContainer = TextPrimary,

    tertiary = BrandViolet,
    onTertiary = Color.White,

    background = BackgroundPrimary,
    onBackground = TextPrimary,

    surface = SurfacePrimary,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceSecondary,
    onSurfaceVariant = TextSecondary,

    outline = BorderSubtle,
    error = StatusError,
    onError = Color.White
)

@Composable
fun StrategyAssistantTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = StrategyDarkColorScheme,
        typography = Typography,
        content = content
    )
}
