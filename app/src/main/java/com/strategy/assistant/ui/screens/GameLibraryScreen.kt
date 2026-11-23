
package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
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
import com.strategy.assistant.ui.localization.appText
import androidx.compose.foundation.layout.width

private val LibraryBlack = Color(0xFF090710)
private val LibraryGold = Color(0xFFD9B76E)
private val LibraryPurple = Color(0xFFAA67FF)
private val LibraryText = Color(0xFFF5F0FA)
private val LibraryMuted = Color(0xFFAAA0B9)

@Composable
fun GameLibraryScreen(
    padding: PaddingValues,
    games: List<String> = emptyList(),
    onAddGame: (String) -> Unit = {},
    onRemoveGame: (String) -> Unit = {}
) {
    var showAddDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LibraryBlack)
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 19.dp)
    ) {
        if (showAddDialog) {
            AddGameDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { name -> onAddGame(name); showAddDialog = false }
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = appText("your_collection"),
            color = LibraryGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = appText("game_library"),
            color = LibraryText,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = appText("library_description"),
            color = LibraryMuted,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(27.dp))

        // LIBRARY SUMMARY
        RowSummary(games.size)

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = appText("my_games"),
            color = LibraryGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.8.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = appText("your_collection"),
            color = LibraryText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(17.dp))

        if (games.isEmpty()) {
        // EMPTY LIBRARY STATE
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF21152F),
                            Color(0xFF15101F),
                            Color(0xFF100D18)
                        )
                    )
                )
                .border(
                    1.dp,
                    Color(0xFF65418A).copy(alpha = 0.65f),
                    RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 22.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2A1A3D))
                    .border(
                        1.dp,
                        LibraryPurple.copy(alpha = 0.55f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "▣",
                    color = LibraryPurple,
                    fontSize = 39.sp
                )
            }

            Spacer(modifier = Modifier.height(19.dp))

            Text(
                text = appText("library_waiting"),
                color = LibraryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = appText("no_games"),
                color = LibraryMuted,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appText("choose_first_game"),
                color = LibraryMuted,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showAddDialog = true }
                    .clip(RoundedCornerShape(13.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF8139C8),
                                Color(0xFF51218A)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        LibraryPurple,
                        RoundedCornerShape(13.dp)
                    )
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (com.strategy.assistant.ui.localization.LocalAppLanguage.current == "العربية") "إضافة لعبة" else "Add a game",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
        }
        }

        if (games.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            games.forEach { game ->
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(game, color = LibraryText, fontSize = 13.sp)
                    Text(
                        text = if (com.strategy.assistant.ui.localization.LocalAppLanguage.current == "العربية") "حذف" else "Remove",
                        color = LibraryPurple, fontSize = 11.sp,
                        modifier = Modifier.clickable { onRemoveGame(game) }.padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // MULTI-GAME SUPPORT
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF14101D))
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(15.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = appText("multi_game_platform"),
                color = LibraryGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = appText("multi_game_tagline"),
                color = LibraryText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = appText("game_modules_description"),
                color = LibraryMuted,
                fontSize = 10.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "STRATEGY ASSISTANT",
            color = Color(0xFF665C73),
            fontSize = 8.sp,
            letterSpacing = 1.2.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun RowSummary(gameCount: Int) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFF15111F))
            .border(
                1.dp,
                Color(0xFF30243D),
                RoundedCornerShape(15.dp)
            )
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = gameCount.toString().padStart(2, '0'),
                color = LibraryGold,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = appText("games_label"),
                color = LibraryMuted,
                fontSize = 8.sp,
                letterSpacing = 1.sp
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(39.dp)
                .background(Color(0xFF3A2B49))
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = appText("ready"),
                color = LibraryPurple,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = appText("platform"),
                color = LibraryMuted,
                fontSize = 8.sp,
                letterSpacing = 1.sp
            )
        }
    }
}
