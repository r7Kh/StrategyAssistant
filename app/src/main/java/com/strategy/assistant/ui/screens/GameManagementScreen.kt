
package com.strategy.assistant.ui.screens

import com.strategy.assistant.ui.localization.appText

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val GameManageBackground = Color(0xFF090710)
private val GameManageSurface = Color(0xFF171220)
private val GameManageGold = Color(0xFFD9B76E)
private val GameManagePurple = Color(0xFFAD68FF)
private val GameManageText = Color(0xFFF5F0FA)
private val GameManageMuted = Color(0xFFAAA0B9)

@Composable
fun GameManagementScreen(
    padding: PaddingValues,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameManageBackground)
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 19.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(GameManageSurface, CircleShape)
                    .border(1.dp, Color(0xFF49345F), CircleShape)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text("‹", color = GameManageText, fontSize = 27.sp)
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(appText("module_control"),
                    color = GameManageGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )

                Text(appText("game_management"),
                    color = GameManageText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                "⌘",
                color = GameManagePurple,
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        // OVERVIEW
        Text(appText("system_overview"),
            color = GameManageGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GameManageSurface, RoundedCornerShape(15.dp))
                .border(1.dp, Color(0xFF30243D), RoundedCornerShape(15.dp))
                .padding(vertical = 17.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GameManageStat("—", appText("installed"))
            GameManageStat("—", appText("active"))
            GameManageStat("—", appText("updates"))
        }

        Spacer(modifier = Modifier.height(27.dp))

        // FEATURED MODULE AREA
        Text(appText("game_modules"),
            color = GameManageGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF28183A),
                            Color(0xFF17111F)
                        )
                    ),
                    RoundedCornerShape(18.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF67458A),
                    RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 20.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFF342147), CircleShape)
                    .border(
                        1.dp,
                        GameManagePurple.copy(alpha = 0.55f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "▣",
                    color = GameManagePurple,
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(appText("no_game_modules"),
                color = GameManageText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(appText("installed_modules_appear"),
                color = GameManageMuted,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(appText("no_game_selected"),
                color = GameManageMuted,
                fontSize = 9.sp
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // ADD MODULE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF7134B6), Color(0xFF4C237D))
                    ),
                    RoundedCornerShape(14.dp)
                )
                .border(
                    1.dp,
                    GameManagePurple,
                    RoundedCornerShape(14.dp)
                )
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("⌛", color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(9.dp))
            Text("Game module management is planned for a later phase",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // MODULE INFORMATION
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GameManageSurface, RoundedCornerShape(14.dp))
                .border(1.dp, Color(0xFF30243D), RoundedCornerShape(14.dp))
                .padding(15.dp)
        ) {
            Text(appText("modular_platform"),
                color = GameManageGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(appText("module_info"),
                color = GameManageMuted,
                fontSize = 10.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun GameManageStat(
    value: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            color = GameManageText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            label,
            color = GameManageMuted,
            fontSize = 8.sp,
            letterSpacing = 0.6.sp
        )
    }
}
