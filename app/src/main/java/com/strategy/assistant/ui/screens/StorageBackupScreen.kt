package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF090710)
private val Surface = Color(0xFF171220)
private val Gold = Color(0xFFD9B76E)
private val Purple = Color(0xFFAD68FF)
private val Txt = Color(0xFFF5F0FA)
private val Muted = Color(0xFFAAA0B9)

@Composable
fun StorageBackupScreen(
    padding: PaddingValues, onBack: () -> Unit, cacheBytes: Long, appBytes: Long,
    onRefresh: () -> Unit, onBackup: () -> Unit, onRestore: () -> Unit, onCleanCache: () -> Unit
) {
    LaunchedEffect(Unit) { onRefresh() }
    val cacheLabel = when {
        cacheBytes < 1024 -> "$cacheBytes B"
        cacheBytes < 1024 * 1024 -> String.format("%.1f KB", cacheBytes / 1024.0)
        else -> String.format("%.2f MB", cacheBytes / (1024.0 * 1024.0))
    }
    Column(Modifier.fillMaxSize().background(Bg).padding(padding).padding(horizontal = 19.dp)) {
        Row(Modifier.fillMaxWidth().padding(vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("‹", Modifier.clickable { onBack() }.padding(8.dp), color = Txt, fontSize = 28.sp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) { Text("DATA MANAGEMENT", color = Gold, fontSize = 9.sp, fontWeight = FontWeight.Bold); Text("Storage & Backup", color = Txt, fontSize = 23.sp, fontWeight = FontWeight.Bold) }
        }
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF251936)), shape = RoundedCornerShape(17.dp)) {
            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                Text("TOTAL APP DATA", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(formatBytes(appBytes), color = Txt, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Text("Total size of the app data directory", color = Muted, fontSize = 10.sp)
                Spacer(Modifier.height(10.dp))
                Text("Temporary cache: $cacheLabel", color = Purple, fontSize = 11.sp)
                Text("Refresh", Modifier.clickable { onRefresh() }.padding(top = 10.dp), color = Purple, fontSize = 11.sp)
            }
        }
        Spacer(Modifier.height(20.dp))
        Text("BACKUP & RESTORE", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(9.dp))
        StorageAction("Export backup", "Save accounts, activity, notifications and app preferences as a JSON file", onBackup)
        StorageAction("Restore backup", "Import a previously exported Strategy Assistant JSON backup", onRestore)
        Spacer(Modifier.height(18.dp))
        Text("CACHE MANAGEMENT", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(9.dp))
        StorageAction("Clean temporary cache", "Remove temporary files only. Exported backups are preserved.", onCleanCache)
        Spacer(Modifier.height(18.dp))
        Text("Cloud backup is not enabled because no cloud provider is connected. Backups are exported locally through Android's file picker.", color = Muted, fontSize = 10.sp, lineHeight = 16.sp)
    }
}

@Composable
private fun StorageAction(title: String, description: String, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(bottom = 9.dp).clickable { onClick() }, colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(14.dp)) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(title, color = Txt, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(description, color = Muted, fontSize = 10.sp, lineHeight = 15.sp)
            }
            Text("›", color = Gold, fontSize = 23.sp)
        }
    }
}

private fun formatBytes(bytes: Long): String = when {
    bytes < 1024 -> "$bytes B"
    bytes < 1024 * 1024 -> String.format("%.1f KB", bytes / 1024.0)
    bytes < 1024L * 1024L * 1024L -> String.format("%.2f MB", bytes / (1024.0 * 1024.0))
    else -> String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0))
}
