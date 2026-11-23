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
import com.strategy.assistant.data.core.ActivityEntry
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
fun ActivityScreen(padding: PaddingValues, onBack: () -> Unit, entries: List<ActivityEntry>, onClear: () -> Unit, use24HourTime: Boolean = true) {
    var filter by remember { mutableStateOf("All") }
    var search by remember { mutableStateOf("") }
    val filters = listOf("All", "Accounts", "System")
    val shown = entries.filter { (filter == "All" || it.category.equals(filter, true)) &&
        (search.isBlank() || it.title.contains(search, true) || it.details.contains(search, true)) }
    Column(Modifier.fillMaxSize().background(Bg).padding(padding).padding(horizontal = 18.dp)) {
        Row(Modifier.fillMaxWidth().padding(vertical = 17.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("‹", Modifier.clickable { onBack() }.padding(8.dp), color = Txt, fontSize = 28.sp)
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) { Text("SYSTEM RECORDS", color = Gold, fontSize = 9.sp, fontWeight = FontWeight.Bold); Text("Activity Center", color = Txt, fontSize = 23.sp, fontWeight = FontWeight.Bold) }
            Text("${entries.size} events", color = Muted, fontSize = 10.sp)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Stat(entries.size.toString(), "Total")
            Stat(entries.count { it.category.equals("Accounts", true) }.toString(), "Accounts")
            Stat(entries.count { it.category.equals("System", true) }.toString(), "System")
        }
        Spacer(Modifier.height(15.dp))
        OutlinedTextField(search, { search = it }, Modifier.fillMaxWidth(), singleLine = true, label = { Text("Search activity") })
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            filters.forEach { item -> FilterChip(selected = filter == item, onClick = { filter = item }, label = { Text(item, fontSize = 10.sp) }) }
        }
        Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("HISTORY", Modifier.weight(1f), color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("Clear history", Modifier.clickable { onClear() }.padding(6.dp), color = Color(0xFFE28A8A), fontSize = 10.sp)
        }
        if (shown.isEmpty()) {
            Card(colors = CardDefaults.cardColors(containerColor = Surface)) {
                Text(if (entries.isEmpty()) "No activity recorded yet." else "No events match this filter.", Modifier.padding(22.dp), color = Muted)
            }
        } else LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(shown, key = { it.id }) { entry ->
                Card(colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(13.dp)) {
                    Column(Modifier.fillMaxWidth().padding(13.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(entry.title, Modifier.weight(1f), color = Txt, fontWeight = FontWeight.SemiBold)
                            Text(entry.category, color = Purple, fontSize = 10.sp)
                        }
                        if (entry.details.isNotBlank()) Text(entry.details, color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                        Text(SimpleDateFormat(if (use24HourTime) "yyyy-MM-dd  HH:mm" else "yyyy-MM-dd  hh:mm a", Locale.getDefault()).format(Date(entry.timestamp)), color = Gold, fontSize = 9.sp, modifier = Modifier.padding(top = 7.dp))
                    }
                }
            }
        }
    }
}

@Composable private fun Stat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = Txt, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        Text(label, color = Muted, fontSize = 9.sp)
    }
}
