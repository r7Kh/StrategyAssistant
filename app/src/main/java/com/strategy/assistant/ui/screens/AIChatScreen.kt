package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.data.ai.AiProviderStore
import com.strategy.assistant.data.ai.DirectAiClient
import kotlinx.coroutines.launch

private data class ChatLine(val role: String, val text: String)

@Composable
fun AIChatScreen(padding: PaddingValues, onBack: () -> Unit, onOpenProviders: () -> Unit) {
    val context = LocalContext.current
    val store = remember(context) { AiProviderStore(context) }
    val client = remember { DirectAiClient() }
    val scope = rememberCoroutineScope()
    var config by remember { mutableStateOf(store.load()) }
    var input by remember { mutableStateOf("") }
    var sending by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val messages = remember { mutableStateListOf<ChatLine>() }

    Column(Modifier.fillMaxSize().background(Color(0xFF090710)).padding(padding)) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Column(Modifier.weight(1f)) {
                Text("INTELLIGENCE CHANNEL", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(config.provider.title + " • " + config.model, color = Color(0xFFD6B56D), fontSize = 10.sp)
            }
            TextButton(onClick = onOpenProviders) { Text("PROVIDERS", color = Color(0xFFD6B56D), fontSize = 10.sp) }
        }
        HorizontalDivider(color = Color(0xFF292332))
        if (config.apiKey.isBlank()) {
            Column(Modifier.fillMaxSize().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("AI CONNECTION REQUIRED", color = Color(0xFFD6B56D), fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
                Spacer(Modifier.height(10.dp))
                Text("Add your own provider API key to activate the Intelligence Channel. Your key stays encrypted on this device.", color = Color(0xFFAAA0B9), fontSize = 12.sp, lineHeight = 19.sp)
                Spacer(Modifier.height(18.dp))
                Button(onClick = onOpenProviders, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6B56D), contentColor = Color(0xFF17111F))) {
                    Text("CONFIGURE AI PROVIDER")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 14.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (messages.isEmpty()) {
                    item {
                        Text("Your strategic intelligence channel is ready. Ask about tactics, game systems, planning or performance.", color = Color(0xFFAAA0B9), fontSize = 12.sp, lineHeight = 19.sp)
                    }
                }
                itemsIndexed(messages) { _, line ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = if (line.role == "user") Arrangement.End else Arrangement.Start) {
                        Surface(
                            color = if (line.role == "user") Color(0xFF302342) else Color(0xFF15121D),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.widthIn(max = 320.dp)
                        ) {
                            Text(line.text, Modifier.padding(13.dp), color = Color(0xFFF1EDF7), fontSize = 13.sp, lineHeight = 19.sp)
                        }
                    }
                }
                if (sending) item { Text("STRATEGY AI IS THINKING…", color = Color(0xFFD6B56D), fontSize = 10.sp) }
                error?.let { message -> item { Text(message, color = Color(0xFFE39B9B), fontSize = 11.sp) } }
            }
            Row(
                Modifier.fillMaxWidth().padding(12.dp).background(Color(0xFF15121D), RoundedCornerShape(16.dp)).padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = input, onValueChange = { input = it },
                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp, vertical = 12.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp),
                    decorationBox = { inner ->
                        if (input.isEmpty()) Text("Write your command…", color = Color(0xFF82798E), fontSize = 13.sp)
                        inner()
                    }
                )
                IconButton(enabled = !sending && input.isNotBlank(), onClick = {
                    val prompt = input.trim()
                    input = ""
                    error = null
                    messages.add(ChatLine("user", prompt))
                    sending = true
                    scope.launch {
                        runCatching {
                            val history = messages.dropLast(1).map { it.role to it.text }
                            client.send(config, prompt, history)
                        }.onSuccess { reply ->
                            messages.add(ChatLine("assistant", reply.text))
                            config = store.load()
                        }.onFailure { failure ->
                            error = failure.message ?: "AI request failed."
                            messages.removeAt(messages.lastIndex)
                        }
                        sending = false
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color(0xFFD6B56D))
                }
            }
        }
    }
}
