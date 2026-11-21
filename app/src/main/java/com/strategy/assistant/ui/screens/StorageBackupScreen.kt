
package com.strategy.assistant.ui.screens

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

private val StorageBackground = Color(0xFF090710)
private val StorageSurface = Color(0xFF171220)
private val StorageGold = Color(0xFFD9B76E)
private val StoragePurple = Color(0xFFAD68FF)
private val StorageText = Color(0xFFF5F0FA)
private val StorageMuted = Color(0xFFAAA0B9)

@Composable
fun StorageBackupScreen(
    padding: PaddingValues,
    onBack: () -> Unit,
    onBackup: () -> Unit = {},
    onRestore: () -> Unit = {},
    onCleanCache: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StorageBackground)
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
                    .background(StorageSurface, CircleShape)
                    .border(1.dp, Color(0xFF49345F), CircleShape)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text("‹", color = StorageText, fontSize = 27.sp)
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "DATA MANAGEMENT",
                    color = StorageGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )

                Text(
                    "Storage & Backup",
                    color = StorageText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                "▤",
                color = StoragePurple,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        // STORAGE OVERVIEW
        Text(
            "STORAGE OVERVIEW",
            color = StorageGold,
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
                        listOf(Color(0xFF28183A), Color(0xFF17111F))
                    ),
                    RoundedCornerShape(18.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF67458A),
                    RoundedCornerShape(18.dp)
                )
                .padding(19.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .border(
                            width = 5.dp,
                            color = StoragePurple.copy(alpha = 0.7f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "—",
                        color = StorageText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(18.dp))

                Column {
                    Text(
                        "Storage Data",
                        color = StorageText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        "Waiting for storage information",
                        color = StorageMuted,
                        fontSize = 9.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        "NOT YET MEASURED",
                        color = StorageGold,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            StorageLegend("App Data", "Not measured", Color(0xFF9A63E8))
            StorageLegend("Game Data", "Not measured", Color(0xFF6578D8))
            StorageLegend("Other Files", "Not measured", Color(0xFFB5A8C8))
            StorageLegend("Free Space", "Not measured", Color(0xFF51445F))
        }

        Spacer(modifier = Modifier.height(25.dp))

        // CLOUD BACKUP
        Text(
            "BACKUP & RESTORE",
            color = StorageGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(StorageSurface, RoundedCornerShape(15.dp))
                .border(1.dp, Color(0xFF30243D), RoundedCornerShape(15.dp))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFF2B1B3D), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "☁",
                        color = StoragePurple,
                        fontSize = 23.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Cloud Backup",
                        color = StorageText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        "Not configured",
                        color = StorageGold,
                        fontSize = 9.sp
                    )
                }

                Text(
                    "OFF",
                    color = StorageMuted,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFF30243D))
            )

            Spacer(modifier = Modifier.height(13.dp))

            StorageInfoRow("Last Backup", "Not available")
            StorageInfoRow("Backup Status", "Not configured")
            StorageInfoRow("Cloud Provider", "Not connected")
        }

        Spacer(modifier = Modifier.height(22.dp))

        // ACTIONS
        Text(
            "STORAGE ACTIONS",
            color = StorageGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        StorageAction(
            symbol = "☁",
            title = "Backup Now",
            description = "Create a backup of supported data",
            onClick = onBackup
        )

        StorageAction(
            symbol = "↻",
            title = "Restore Data",
            description = "Restore a previous backup",
            onClick = onRestore
        )

        StorageAction(
            symbol = "▤",
            title = "Clean Cache",
            description = "Manage temporary application files",
            onClick = onCleanCache
        )

        Spacer(modifier = Modifier.height(23.dp))

        // DATA POLICY
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF17121D), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0xFF30243D), RoundedCornerShape(14.dp))
                .padding(15.dp)
        ) {
            Text(
                "DATA & PRIVACY",
                color = StorageGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                "Only selected persistent application data will be included in future backups. Temporary game-session data and extracted screenshots will not be stored as permanent backup data.",
                color = StorageMuted,
                fontSize = 10.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun StorageLegend(
    title: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )

        Spacer(modifier = Modifier.width(9.dp))

        Text(
            title,
            color = StorageText,
            fontSize = 10.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            value,
            color = StorageMuted,
            fontSize = 9.sp
        )
    }
}

@Composable
private fun StorageInfoRow(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            color = StorageMuted,
            fontSize = 10.sp
        )

        Text(
            value,
            color = StorageText,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StorageAction(
    symbol: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .background(StorageSurface, RoundedCornerShape(13.dp))
            .border(1.dp, Color(0xFF30243D), RoundedCornerShape(13.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(Color(0xFF2B1B3D), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                symbol,
                color = StoragePurple,
                fontSize = 19.sp
            )
        }

        Spacer(modifier = Modifier.width(11.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                color = StorageText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                description,
                color = StorageMuted,
                fontSize = 9.sp
            )
        }

        Text(
            "›",
            color = StorageGold,
            fontSize = 22.sp
        )
    }
}
