
package com.strategy.assistant.ui.design

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object StrategyColors {

    // Backgrounds
    val Background = Color(0xFF08070D)
    val BackgroundSecondary = Color(0xFF0D0B15)
    val BackgroundElevated = Color(0xFF14101F)

    // Surfaces
    val Surface = Color(0xFF171323)
    val SurfaceLight = Color(0xFF211A31)
    val SurfaceGlass = Color(0xCC1B1628)

    // Purple identity
    val Purple = Color(0xFF7040D8)
    val PurpleLight = Color(0xFFA78BFA)
    val PurpleDark = Color(0xFF351B68)
    val Violet = Color(0xFF8B5CF6)

    // Royal gold
    val Gold = Color(0xFFD6B56D)
    val GoldLight = Color(0xFFE8D19B)
    val GoldDark = Color(0xFF8C6A32)

    // Text
    val TextPrimary = Color(0xFFF4F0FA)
    val TextSecondary = Color(0xFFAAA3BA)
    val TextMuted = Color(0xFF716A80)

    // Status
    val Success = Color(0xFF4ADE80)
    val Warning = Color(0xFFFBBF24)
    val Error = Color(0xFFEF4444)
    val Info = Color(0xFF60A5FA)

    // Borders
    val Border = Color(0xFF352B49)
    val BorderGold = Color(0x66D6B56D)
}

object StrategyGradients {

    val RoyalPurple = Brush.linearGradient(
        colors = listOf(
            Color(0xFF32185C),
            Color(0xFF171124),
            Color(0xFF0C0A13)
        )
    )

    val GoldGlow = Brush.linearGradient(
        colors = listOf(
            StrategyColors.GoldLight,
            StrategyColors.Gold,
            StrategyColors.GoldDark
        )
    )

    val DarkBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF100C19),
            Color(0xFF08070D),
            Color(0xFF050509)
        )
    )
}

object StrategyShapes {

    val Small = RoundedCornerShape(10.dp)
    val Medium = RoundedCornerShape(16.dp)
    val Large = RoundedCornerShape(22.dp)
    val ExtraLarge = RoundedCornerShape(28.dp)
    val Pill = RoundedCornerShape(50)
}

object StrategySpacing {

    val XSmall = 4.dp
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val XLarge = 24.dp
    val XXLarge = 32.dp
    val Section = 40.dp
}
