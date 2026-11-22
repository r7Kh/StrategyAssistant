
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AccountBackground = Color(0xFF090710)
private val AccountSurface = Color(0xFF171220)
private val AccountGold = Color(0xFFD9B76E)
private val AccountPurple = Color(0xFFAD68FF)
private val AccountText = Color(0xFFF5F0FA)
private val AccountMuted = Color(0xFFAAA0B9)

@Composable
fun AccountsScreen(
    padding: PaddingValues,
    onBack: () -> Unit,
    onAddAccount: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AccountBackground)
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
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
                    .clip(CircleShape)
                    .background(AccountSurface)
                    .border(1.dp, Color(0xFF49345F), CircleShape)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "‹",
                    color = AccountText,
                    fontSize = 27.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = appText("account_management"),
                    color = AccountGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )

                Text(
                    text = appText("accounts"),
                    color = AccountText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "♙",
                color = AccountPurple,
                fontSize = 23.sp
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ACTIVE ACCOUNT
        Text(
            text = appText("active_account"),
            color = AccountGold,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(17.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF291A3D),
                            Color(0xFF17111F)
                        )
                    )
                )
                .border(
                    1.dp,
                    Color(0xFF67458A),
                    RoundedCornerShape(17.dp)
                )
                .padding(19.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF342147))
                    .border(
                        1.dp,
                        AccountPurple.copy(alpha = 0.6f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "♙",
                    color = AccountPurple,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = appText("no_active_account"),
                color = AccountText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = appText("account_not_connected"),
                color = AccountMuted,
                fontSize = 10.sp
            )

            Text(
                text = appText("add_account_start"),
                color = AccountMuted,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(Color(0xFFF0B95E), CircleShape)
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = appText("not_configured"),
                    color = AccountGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // OTHER ACCOUNTS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = appText("other_accounts"),
                color = AccountGold,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Text(
                text = appText("zero_accounts"),
                color = AccountMuted,
                fontSize = 9.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(AccountSurface)
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(14.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(39.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(Color(0xFF281B38)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "◈",
                    color = AccountPurple,
                    fontSize = 21.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = appText("no_additional_accounts"),
                    color = AccountText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = appText("connected_accounts_appear"),
                    color = AccountMuted,
                    fontSize = 9.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // ADD ACCOUNT BUTTON
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF7134B6),
                            Color(0xFF4C237D)
                        )
                    )
                )
                .border(
                    1.dp,
                    AccountPurple,
                    RoundedCornerShape(14.dp)
                )
                .clickable { onAddAccount() }
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+",
                color = Color.White,
                fontSize = 21.sp
            )

            Spacer(modifier = Modifier.width(9.dp))

            Text(
                text = appText("add_account"),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // INFORMATION
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF13101B))
                .border(
                    1.dp,
                    Color(0xFF30243D),
                    RoundedCornerShape(14.dp)
                )
                .padding(15.dp)
        ) {
            Text(
                text = appText("account_security"),
                color = AccountGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = appText("account_security_note"),
                color = AccountMuted,
                fontSize = 10.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}
