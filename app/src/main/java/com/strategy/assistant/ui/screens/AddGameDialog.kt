package com.strategy.assistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.strategy.assistant.ui.localization.LocalAppLanguage

@Composable
fun AddGameDialog(
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    val arabic = LocalAppLanguage.current == "العربية"
    val catalog = listOf(
        "Dragon Siege", "Rise of Kingdoms", "Clash of Clans",
        "Whiteout Survival", "Last War: Survival", "Evony",
        "Call of Dragons", "Arena Breakout", "PUBG Mobile",
        "Free Fire", "Mobile Legends", "State of Survival"
    )
    var name by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (arabic) "إضافة لعبة" else "Add a game") },
        text = {
            Column {
                Text(if (arabic) "اختر لعبة من القائمة أو اكتب اسمها بنفسك." else "Choose a game from the list or type its name.")
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(if (arabic) "اسم اللعبة" else "Game name") },
                    singleLine = true
                )
                TextButton(onClick = { expanded = true }) {
                    Text(if (arabic) "عرض الألعاب الجاهزة" else "Browse game list")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    catalog.forEach { game ->
                        DropdownMenuItem(
                            text = { Text(game) },
                            onClick = {
                                name = game
                                expanded = false
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = name.isNotBlank(),
                onClick = { onAdd(name.trim()) }
            ) { Text(if (arabic) "إضافة" else "Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (arabic) "إلغاء" else "Cancel")
            }
        }
    )
}
