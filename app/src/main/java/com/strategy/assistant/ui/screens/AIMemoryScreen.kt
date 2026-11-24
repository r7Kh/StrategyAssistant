package com.strategy.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.data.ai.AiMemoryItem
import com.strategy.assistant.data.ai.AiMemoryRepository
import kotlinx.coroutines.launch

private val MemoryBackground = Color(0xFF090710)
private val MemoryGold = Color(0xFFD6B56D)
private val MemoryMuted = Color(0xFFAAA0B9)
private val MemoryCard = Color(0xFF15121D)

@Composable
fun AIMemoryScreen(
    padding: PaddingValues,
    repository: AiMemoryRepository,
    isArabic: Boolean,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snapshot by repository.snapshot.collectAsState(initial = null)
    var showEditor by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<AiMemoryItem?>(null) }
    var showClearConfirm by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General") }
    var editorError by remember { mutableStateOf<String?>(null) }

    fun openEditor(item: AiMemoryItem? = null) {
        editing = item
        content = item?.content.orEmpty()
        category = item?.category ?: "General"
        editorError = null
        showEditor = true
    }

    Column(
        Modifier.fillMaxSize().background(MemoryBackground).padding(padding).padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Column(Modifier.weight(1f)) {
                Text(if (isArabic) "ذاكرة الذكاء الاصطناعي" else "AI CONVERSATION MEMORY",
                    color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(if (isArabic) "تحكم بالمعلومات التي يتذكرها المساعد" else "Control what your assistant remembers",
                    color = MemoryMuted, fontSize = 11.sp)
            }
        }

        Surface(color = MemoryCard, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(if (isArabic) "الذاكرة الشخصية" else "PERSONAL MEMORY",
                            color = MemoryGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text(
                            if (isArabic) "عند التفعيل، تُحفظ المعلومات الواضحة التي تذكرها عن نفسك وتفضيلاتك." else "When enabled, clear facts you share about yourself and your preferences can be saved locally.",
                            color = MemoryMuted, fontSize = 11.sp, lineHeight = 17.sp
                        )
                    }
                    Switch(
                        checked = snapshot?.enabled ?: true,
                        onCheckedChange = { enabled -> scope.launch { repository.setEnabled(enabled) } },
                        colors = SwitchDefaults.colors(checkedThumbColor = MemoryGold, checkedTrackColor = Color(0xFF59436F))
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    if (isArabic) "تُخزّن الذاكرة على هذا الجهاز فقط، ويمكنك تعديلها أو حذفها في أي وقت." else "Memory stays on this device. You can edit or delete any item at any time.",
                    color = MemoryMuted, fontSize = 10.sp
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(if (isArabic) "المعلومات المحفوظة" else "SAVED MEMORIES",
                    color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text("${snapshot?.items?.size ?: 0} / 100", color = MemoryMuted, fontSize = 10.sp)
            }
            TextButton(onClick = { openEditor() }) {
                Icon(Icons.Default.Add, contentDescription = null, tint = MemoryGold)
                Spacer(Modifier.width(4.dp))
                Text(if (isArabic) "إضافة" else "ADD", color = MemoryGold)
            }
            if (!snapshot?.items.isNullOrEmpty()) {
                TextButton(onClick = { showClearConfirm = true }) {
                    Text(if (isArabic) "مسح الكل" else "CLEAR", color = Color(0xFFE39B9B), fontSize = 10.sp)
                }
            }
        }

        if (snapshot?.items.isNullOrEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(if (isArabic) "لا توجد ذكريات محفوظة بعد" else "NO SAVED MEMORIES",
                        color = MemoryGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(if (isArabic) "يمكنك إضافة معلومة يدوياً أو التحدث عن تفضيلاتك." else "Add a memory manually or share a clear preference in chat.",
                        color = MemoryMuted, fontSize = 11.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                items(snapshot?.items.orEmpty(), key = { it.id }) { item ->
                    Surface(color = MemoryCard, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(item.category.uppercase(), color = MemoryGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(4.dp))
                                Text(item.content, color = Color(0xFFF1EDF7), fontSize = 13.sp, lineHeight = 18.sp)
                            }
                            IconButton(onClick = { openEditor(item) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MemoryMuted)
                            }
                            IconButton(onClick = { scope.launch { repository.delete(item.id) } }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFE39B9B))
                            }
                        }
                    }
                }
            }
        }
    }

    if (showEditor) {
        AlertDialog(
            onDismissRequest = { showEditor = false },
            containerColor = Color(0xFF1A1523),
            title = {
                Text(if (isArabic) if (editing == null) "إضافة ذكرى" else "تعديل الذكرى"
                    else if (editing == null) "Add memory" else "Edit memory", color = Color.White)
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = category, onValueChange = { category = it.take(32) },
                        label = { Text(if (isArabic) "التصنيف" else "Category") }, singleLine = true
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = content, onValueChange = { content = it.take(240) },
                        label = { Text(if (isArabic) "المعلومة" else "Memory") },
                        minLines = 2, maxLines = 4
                    )
                    editorError?.let { Text(it, color = Color(0xFFE39B9B), fontSize = 11.sp) }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        runCatching {
                            val current = editing
                            if (current == null) repository.add(category, content)
                            else repository.update(current.id, category, content)
                        }.onSuccess { showEditor = false }
                            .onFailure { editorError = it.message ?: "Could not save memory." }
                    }
                }) { Text(if (isArabic) "حفظ" else "SAVE", color = MemoryGold) }
            },
            dismissButton = {
                TextButton(onClick = { showEditor = false }) {
                    Text(if (isArabic) "إلغاء" else "CANCEL", color = MemoryMuted)
                }
            }
        )
    }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            containerColor = Color(0xFF1A1523),
            title = { Text(if (isArabic) "مسح كل الذكريات؟" else "Clear all memories?", color = Color.White) },
            text = { Text(if (isArabic) "لا يمكن التراجع عن هذا الإجراء." else "This cannot be undone.", color = MemoryMuted) },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch { repository.clearAll(); showClearConfirm = false }
                }) { Text(if (isArabic) "مسح الكل" else "CLEAR ALL", color = Color(0xFFE39B9B)) }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) {
                    Text(if (isArabic) "إلغاء" else "CANCEL", color = MemoryMuted)
                }
            }
        )
    }
}
