package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.data.core.AppNotification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Bg = Color(0xFF090710)
private val Surface = Color(0xFF171220)
private val Gold = Color(0xFFD9B76E)
private val Purple = Color(0xFFAD68FF)
private val Txt = Color(0xFFF5F0FA)
private val Muted = Color(0xFFAAA0B9)

@Composable
fun NotificationsScreen(
    padding: PaddingValues, onBack: () -> Unit, notifications: List<AppNotification>,
    onMarkRead: (String) -> Unit, onMarkAllRead: () -> Unit, onDelete: (String) -> Unit, onClear: () -> Unit, use24HourTime: Boolean = true, onCreateTest: () -> Unit = {}
) {
    var filter by remember { mutableStateOf("All") }
    val shown = notifications.filter {
        when (filter) {
            "Unread" -> !it.isRead
            "Updates" -> it.category.equals("Updates", true)
            "System" -> it.category.equals("System", true) || it.category.equals("Accounts", true)
            else -> true
        }
    }
    Column(Modifier.fillMaxSize().background(Bg).padding(padding).padding(horizontal = 18.dp)) {
        Row(Modifier.fillMaxWidth().padding(vertical = 17.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("‹", Modifier.clickable { onBack() }.padding(8.dp), color = Txt, fontSize = 28.sp)
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) { Text("SYSTEM MESSAGES", color = Gold, fontSize = 9.sp, fontWeight = FontWeight.Bold); Text("Notifications", color = Txt, fontSize = 23.sp, fontWeight = FontWeight.Bold) }
            Text("${notifications.count { !it.isRead }} unread", color = Purple, fontSize = 10.sp)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Stat(notifications.size.toString(), "Total")
            Stat(notifications.count { !it.isRead }.toString(), "Unread")
            Stat(notifications.count { it.category.equals("Updates", true) }.toString(), "Updates")
        }
        Spacer(Modifier.height(15.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            listOf("All", "Unread", "Updates", "System").forEach { item ->
                FilterChip(selected = filter == item, onClick = { filter = item }, label = { Text(item, fontSize = 10.sp) })
            }
        }
        Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("NOTIFICATION LIST", Modifier.weight(1f), color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("Read all", Modifier.clickable { onMarkAllRead() }.padding(5.dp), color = Purple, fontSize = 10.sp)
            Text("Test", Modifier.clickable { onCreateTest() }.padding(5.dp), color = Gold, fontSize = 10.sp)
            Text("Clear", Modifier.clickable { onClear() }.padding(5.dp), color = Color(0xFFE28A8A), fontSize = 10.sp)
        }
        if (shown.isEmpty()) Card(colors = CardDefaults.cardColors(containerColor = Surface)) {
            Text("You're all caught up. New notifications will appear here.", Modifier.padding(22.dp), color = Muted)
        } else LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(shown, key = { it.id }) { item ->
                Card(colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(13.dp)) {
                    Column(Modifier.fillMaxWidth().clickable { onMarkRead(item.id) }.padding(13.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(item.title, Modifier.weight(1f), color = Txt, fontWeight = FontWeight.SemiBold)
                            Text(if (item.isRead) "READ" else "NEW", color = if (item.isRead) Muted else Gold, fontSize = 9.sp)
                        }
                        Text(item.message, color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 5.dp))
                        Row(Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("${item.category} • ${SimpleDateFormat(if (use24HourTime) "MM-dd HH:mm" else "MM-dd hh:mm a", Locale.getDefault()).format(Date(item.timestamp))}", Modifier.weight(1f), color = Purple, fontSize = 9.sp)
                            Text("Delete", Modifier.clickable { onDelete(item.id) }.padding(5.dp), color = Color(0xFFE28A8A), fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable private fun Stat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(value, color = Txt, fontSize = 19.sp, fontWeight = FontWeight.Bold); Text(label, color = Muted, fontSize = 9.sp) }
}
