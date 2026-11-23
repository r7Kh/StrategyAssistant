package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.strategy.assistant.data.core.GamingAccount

private val Bg = Color(0xFF090710)
private val Surface = Color(0xFF171220)
private val Gold = Color(0xFFD9B76E)
private val Purple = Color(0xFFAD68FF)
private val Txt = Color(0xFFF5F0FA)
private val Muted = Color(0xFFAAA0B9)

@Composable
fun AccountsScreen(
    padding: PaddingValues,
    onBack: () -> Unit,
    accounts: List<GamingAccount>,
    onAdd: (String, String, String, String) -> Unit,
    onUpdate: (GamingAccount) -> Unit,
    onActivate: (String) -> Unit,
    onRemove: (String) -> Unit
) {
    var showEditor by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<GamingAccount?>(null) }
    Column(Modifier.fillMaxSize().background(Bg).padding(padding).padding(horizontal = 19.dp)) {
        Row(Modifier.fillMaxWidth().padding(vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("‹", Modifier.clickable { onBack() }.padding(8.dp), color = Txt, fontSize = 28.sp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("ACCOUNT MANAGEMENT", color = Gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                Text("Accounts", color = Txt, fontSize = 23.sp, fontWeight = FontWeight.Bold)
            }
            Text("${accounts.size} total", color = Muted, fontSize = 11.sp)
        }
        val active = accounts.firstOrNull { it.isActive }
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF251936)), shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.fillMaxWidth().padding(17.dp)) {
                Text("ACTIVE ACCOUNT", color = Gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(7.dp))
                Text(active?.title ?: "No active account", color = Txt, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(active?.let { "${it.platform} • ${it.username.ifBlank { "No username" }}" } ?: "Add an account and select it to make it active.", color = Muted, fontSize = 11.sp)
            }
        }
        Spacer(Modifier.height(18.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("YOUR ACCOUNTS", Modifier.weight(1f), color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Button(onClick = { editing = null; showEditor = true }, colors = ButtonDefaults.buttonColors(containerColor = Purple)) { Text("+ Add account") }
        }
        Spacer(Modifier.height(10.dp))
        if (accounts.isEmpty()) {
            Card(colors = CardDefaults.cardColors(containerColor = Surface)) {
                Text("No accounts yet. Add a game account profile to manage it here.", Modifier.padding(20.dp), color = Muted)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                items(accounts, key = { it.id }) { account ->
                    Card(colors = CardDefaults.cardColors(containerColor = Surface), shape = RoundedCornerShape(14.dp)) {
                        Column(Modifier.fillMaxWidth().padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(account.title, color = Txt, fontWeight = FontWeight.Bold)
                                    Text("${account.platform} • ${account.username.ifBlank { "No username" }}", color = Muted, fontSize = 11.sp)
                                }
                                if (account.isActive) Text("ACTIVE", color = Gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                            if (account.notes.isNotBlank()) Text(account.notes, color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 7.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 10.dp)) {
                                Text(if (account.isActive) "Selected" else "Set active", Modifier.clickable { onActivate(account.id) }.padding(5.dp), color = Purple, fontSize = 11.sp)
                                Text("Edit", Modifier.clickable { editing = account; showEditor = true }.padding(5.dp), color = Gold, fontSize = 11.sp)
                                Text("Delete", Modifier.clickable { onRemove(account.id) }.padding(5.dp), color = Color(0xFFE28A8A), fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(18.dp))
        Text("Account profiles store descriptive details only. Passwords and login credentials are not collected or stored.", color = Muted, fontSize = 10.sp)
    }
    if (showEditor) AccountEditor(editing, onDismiss = { showEditor = false }, onSave = { title, platform, username, notes ->
        if (editing == null) onAdd(title, platform, username, notes)
        else onUpdate(editing!!.copy(title = title, platform = platform, username = username, notes = notes))
        showEditor = false
    })
}

@Composable
private fun AccountEditor(account: GamingAccount?, onDismiss: () -> Unit, onSave: (String, String, String, String) -> Unit) {
    var title by remember(account) { mutableStateOf(account?.title.orEmpty()) }
    var platform by remember(account) { mutableStateOf(account?.platform.orEmpty()) }
    var username by remember(account) { mutableStateOf(account?.username.orEmpty()) }
    var notes by remember(account) { mutableStateOf(account?.notes.orEmpty()) }
    AlertDialog(onDismissRequest = onDismiss, containerColor = Surface,
        title = { Text(if (account == null) "Add account" else "Edit account", color = Txt) },
        text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(title, { title = it }, label = { Text("Account name") }, singleLine = true)
            OutlinedTextField(platform, { platform = it }, label = { Text("Platform / game") }, singleLine = true)
            OutlinedTextField(username, { username = it }, label = { Text("Username (optional)") }, singleLine = true)
            OutlinedTextField(notes, { notes = it }, label = { Text("Notes (optional)") })
        } },
        confirmButton = { TextButton(onClick = { if (title.isNotBlank()) onSave(title, platform, username, notes) }, enabled = title.isNotBlank()) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
