
package com.strategy.assistant.ui.design

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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

@Composable
fun RoyalPanel(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(StrategyShapes.Large)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF211832),
                        Color(0xFF171222),
                        Color(0xFF100D18)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        StrategyColors.Gold.copy(alpha = 0.35f),
                        StrategyColors.PurpleLight.copy(alpha = 0.35f),
                        StrategyColors.Border.copy(alpha = 0.5f)
                    )
                ),
                shape = StrategyShapes.Large
            )
            .padding(18.dp)
    ) {
        content()
    }
}

@Composable
fun RoyalSectionTitle(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(17.dp)
                    .clip(CircleShape)
                    .background(StrategyColors.Gold)
            )

            Text(
                text = title.uppercase(),
                color = StrategyColors.TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp
            )
        }

        if (subtitle != null) {
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = subtitle,
                color = StrategyColors.TextMuted,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun SystemStatus(
    label: String,
    value: String,
    color: Color = StrategyColors.Success,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = StrategyColors.TextSecondary,
            fontSize = 12.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color, CircleShape)
            )

            Text(
                text = value,
                color = StrategyColors.TextPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun RoyalDivider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        StrategyColors.Gold.copy(alpha = 0.35f),
                        StrategyColors.Purple.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                )
            )
    )
}

@Composable
fun MetricDisplay(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = value,
            color = StrategyColors.GoldLight,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label.uppercase(),
            color = StrategyColors.TextMuted,
            fontSize = 9.sp,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun RoyalBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(StrategyColors.Gold.copy(alpha = 0.10f))
            .border(
                1.dp,
                StrategyColors.Gold.copy(alpha = 0.35f),
                CircleShape
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            color = StrategyColors.GoldLight,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}
