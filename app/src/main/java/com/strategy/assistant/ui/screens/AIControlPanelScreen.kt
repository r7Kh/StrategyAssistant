package com.strategy.assistant.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.data.ai.AiProvider
import com.strategy.assistant.data.ai.AiProviderConfig
import com.strategy.assistant.data.ai.AiProviderStore

private val PanelGold = Color(0xFFD6B46A)
private val PanelBackground = Color(0xFF090811)
private val PanelCard = Color(0xFF151321)
private val PanelMuted = Color(0xFF9692A5)

@Composable
fun AIControlPanelScreen(padding: PaddingValues, onBack: () -> Unit) {
    val context = LocalContext.current
    val store = remember(context) { AiProviderStore(context) }
    val saved = remember { store.load() }
    var provider by remember { mutableStateOf(saved.provider) }
    var apiKey by remember { mutableStateOf("") }
    var model by remember { mutableStateOf(saved.model) }
    var baseUrl by remember { mutableStateOf(saved.baseUrl) }
    var keyVisible by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    var hasSavedKey by remember { mutableStateOf(saved.apiKey.isNotBlank()) }

    Column(
        modifier = Modifier.fillMaxSize().background(PanelBackground).padding(padding)
            .padding(horizontal = 20.dp).verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Column {
                Text("AI PROVIDER MANAGER", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text("Bring Your Own API Key", color = PanelMuted, fontSize = 12.sp)
            }
        }

        Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), color = PanelCard) {
            Column(Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Key, contentDescription = null, tint = PanelGold)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("YOUR AI, YOUR ACCOUNT", color = PanelGold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
                        Text("Requests go directly from your device to the provider.", color = PanelMuted, fontSize = 11.sp)
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text("PROVIDER", color = PanelMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(7.dp))
                Box {
                    OutlinedButton(onClick = { menuExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(provider.title, color = Color.White, modifier = Modifier.weight(1f))
                        Text("⌄", color = PanelGold)
                    }
                    DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                        AiProvider.entries.forEach { item ->
                            DropdownMenuItem(text = { Text(item.title) }, onClick = {
                                provider = item
                                hasSavedKey = store.hasKey(item)
                                if (item != AiProvider.CUSTOM) baseUrl = item.defaultBaseUrl
                                if (item.defaultModel.isNotBlank()) model = item.defaultModel
                                menuExpanded = false
                            })
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = apiKey, onValueChange = { apiKey = it }, modifier = Modifier.fillMaxWidth(),
                    label = { Text(if (hasSavedKey) "API Key (saved — leave blank to keep)" else "API Key") },
                    singleLine = true,
                    visualTransformation = if (keyVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = { TextButton(onClick = { keyVisible = !keyVisible }) { Text(if (keyVisible) "Hide" else "Show") } }
                )
                Spacer(Modifier.height(13.dp))
                OutlinedTextField(
                    value = model, onValueChange = { model = it }, modifier = Modifier.fillMaxWidth(),
                    label = { Text("Model ID") }, singleLine = true
                )
                if (provider == AiProvider.CUSTOM) {
                    Spacer(Modifier.height(13.dp))
                    OutlinedTextField(
                        value = baseUrl, onValueChange = { baseUrl = it }, modifier = Modifier.fillMaxWidth(),
                        label = { Text("OpenAI-compatible Base URL") }, singleLine = true,
                        placeholder = { Text("https://your-provider.example") }
                    )
                }
                Spacer(Modifier.height(18.dp))
                Button(
                    onClick = {
                        runCatching {
                            store.save(AiProviderConfig(provider, apiKey, model, baseUrl))
                            hasSavedKey = store.hasKey(provider)
                            apiKey = ""
                            Toast.makeText(context, "AI provider settings saved securely", Toast.LENGTH_SHORT).show()
                        }.onFailure {
                            Toast.makeText(context, "Could not save settings: " + it.message, Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PanelGold, contentColor = Color(0xFF17111F))
                ) { Text("SAVE PROVIDER", fontWeight = FontWeight.Bold) }

                if (hasSavedKey) {
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = {
                        store.clearKey(provider)
                        hasSavedKey = false
                        apiKey = ""
                        Toast.makeText(context, "Saved API key removed", Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.align(Alignment.End)) {
                        Text("Remove saved API key", color = Color(0xFFE39B9B))
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color(0xFF211B30)) {
            Row(Modifier.padding(15.dp), verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = PanelGold, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
                Text(
                    "Your API key is encrypted with Android Keystore and stored on this device. You pay the provider directly. Never share your key. Provider terms, usage limits and charges still apply.",
                    color = PanelMuted, fontSize = 11.sp, lineHeight = 17.sp
                )
            }
        }
        Spacer(Modifier.height(30.dp))
    }
}
