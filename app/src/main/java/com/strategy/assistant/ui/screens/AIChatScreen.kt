package com.strategy.assistant.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Close
import com.strategy.assistant.data.ai.AiImageAttachment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strategy.assistant.data.ai.AiMemoryRepository
import com.strategy.assistant.data.ai.AiProviderStore
import com.strategy.assistant.data.ai.DirectAiClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private data class ChatLine(
    val role: String,
    val text: String,
)

@Composable
fun AIChatScreen(
    padding: PaddingValues,
    onBack: () -> Unit,
    onOpenProviders: () -> Unit,
    onOpenMemory: () -> Unit
) {
    val context = LocalContext.current
    val store = remember(context) { AiProviderStore(context) }
    val memoryRepository = remember(context) { AiMemoryRepository(context) }
    val client = remember { DirectAiClient() }
    val scope = rememberCoroutineScope()
    var config by remember { mutableStateOf(store.load()) }
    var input by remember { mutableStateOf("") }
    var sending by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var imageAttachment by remember { mutableStateOf<AiImageAttachment?>(null) }
    var imageLabel by remember { mutableStateOf<String?>(null) }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            scope.launch {
                runCatching {
                    val mime = context.contentResolver.getType(uri)?.lowercase()
                        ?: throw IllegalArgumentException("Could not identify the image type.")
                    require(mime in AiImageAttachment.SUPPORTED_MIME_TYPES) { "Choose a JPG, PNG, or WebP image." }
                    val bytes = context.contentResolver.openInputStream(uri)?.use { stream ->
                        val output = java.io.ByteArrayOutputStream()
                        val buffer = ByteArray(8192)
                        var total = 0
                        while (true) {
                            val count = stream.read(buffer)
                            if (count < 0) break
                            total += count
                            require(total <= AiImageAttachment.MAX_BYTES) { "Image must be smaller than 8 MB." }
                            output.write(buffer, 0, count)
                        }
                        output.toByteArray()
                    } ?: throw IllegalArgumentException("Could not read the selected image.")
                    AiImageAttachment(bytes, mime)
                }.onSuccess { attachment ->
                    imageAttachment = attachment
                    imageLabel = "Village screenshot attached"
                    error = null
                }.onFailure { failure ->
                    imageAttachment = null
                    imageLabel = null
                    error = failure.message ?: "Could not attach image."
                }
            }
        }
    }
    val messages = remember { mutableStateListOf<ChatLine>() }

    Box(Modifier.fillMaxSize().background(Color(0xFF080610))) {
        AnimatedIntelligenceBackdrop(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize().padding(padding).imePadding()) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Column(Modifier.weight(1f)) {
                    Text("INTELLIGENCE CHANNEL", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp, letterSpacing = 1.05.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(6.dp).background(Color(0xFFD6B56D), CircleShape))
                        Spacer(Modifier.width(6.dp))
                        Text(config.provider.title + " • " + config.model, color = Color(0xFFC8B78E), fontSize = 10.sp)
                    }
                }
                TextButton(onClick = onOpenMemory, contentPadding = PaddingValues(horizontal = 5.dp)) {
                    Text("MEMORY", color = Color(0xFFD6B56D), fontSize = 9.sp, letterSpacing = 0.6.sp)
                }
                TextButton(onClick = onOpenProviders, contentPadding = PaddingValues(horizontal = 5.dp)) {
                    Text("PROVIDERS", color = Color(0xFFD6B56D), fontSize = 9.sp, letterSpacing = 0.6.sp)
                }
            }

            Box(Modifier.fillMaxWidth().height(1.dp).background(
                Brush.horizontalGradient(listOf(Color.Transparent, Color(0xFF765A9B), Color(0xFFD6B56D), Color.Transparent))
            ))

            if (config.apiKey.isBlank()) {
                Column(
                    Modifier.weight(1f).fillMaxWidth().padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFD6B56D), modifier = Modifier.size(38.dp))
                    Spacer(Modifier.height(14.dp))
                    Text("AI CONNECTION REQUIRED", color = Color(0xFFD6B56D), fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Add your own provider API key to activate the Intelligence Channel. Your key stays encrypted on this device.",
                        color = Color(0xFFAAA0B9), fontSize = 12.sp, lineHeight = 19.sp, textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(18.dp))
                    Button(
                        onClick = onOpenProviders,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6B56D), contentColor = Color(0xFF17111F))
                    ) { Text("CONFIGURE AI PROVIDER") }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 13.dp),
                    contentPadding = PaddingValues(top = 18.dp, bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    if (messages.isEmpty()) item { WelcomeIntelligencePanel() }
                    itemsIndexed(messages) { _, line ->
                        ChatMessage(line = line)
                    }
                    if (sending) item { ThinkingIndicator() }
                    error?.let { message ->
                        item {
                            Text(
                                message, Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                color = Color(0xFFE39B9B), fontSize = 11.sp, lineHeight = 17.sp,
                                style = TextStyle(textDirection = TextDirection.Content)
                            )
                        }
                    }
                }

                if (imageAttachment != null) {
                    Row(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Image, contentDescription = null, tint = Color(0xFFD6B56D), modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(7.dp))
                        Text(imageLabel ?: "Image attached", color = Color(0xFFD6B56D), fontSize = 11.sp, modifier = Modifier.weight(1f), maxLines = 1)
                        IconButton(onClick = { imageAttachment = null; imageLabel = null }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Remove image", tint = Color(0xFFB9AEC8), modifier = Modifier.size(15.dp))
                        }
                    }
                }
                Row(
                    Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, bottom = 10.dp, top = 6.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brush.verticalGradient(listOf(Color(0xF01D1729), Color(0xF0100D18))))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.weight(1f).padding(horizontal = 11.dp, vertical = 10.dp),
                        textStyle = TextStyle(color = Color(0xFFF6F1FA), fontSize = 14.sp, lineHeight = 21.sp, textDirection = TextDirection.Content),
                        cursorBrush = SolidColor(Color(0xFFD6B56D)),
                        minLines = 1,
                        maxLines = 5,
                        decorationBox = { inner ->
                            Box {
                                if (input.isEmpty()) Text("Write your message…", color = Color(0xFF8F849F), fontSize = 13.sp)
                                inner()
                            }
                        }
                    )
                    IconButton(
                        enabled = !sending,
                        onClick = { imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
                    ) {
                        Icon(Icons.Default.Image, contentDescription = "Attach image", tint = Color(0xFFD6B56D))
                    }
                    IconButton(
                        enabled = !sending && (input.isNotBlank() || imageAttachment != null),
                        onClick = {
                            val selectedImage = imageAttachment
                            val prompt = input.trim().ifBlank {
                                "Analyze this Clash of Clans village screenshot for a farming attack. Identify visible resource buildings, defenses, a suitable entry side, and a cautious deployment plan. Clearly state anything you cannot identify."
                            }
                            input = ""
                            imageAttachment = null
                            imageLabel = null
                            error = null
                            messages.add(ChatLine("user", if (selectedImage != null) "$prompt\n[Village screenshot attached]" else prompt))
                            sending = true
                            scope.launch {
                                runCatching {
                                    memoryRepository.rememberExplicitFacts(prompt)
                                    val history = messages.dropLast(1).map { it.role to it.text }
                                    val memoryContext = memoryRepository.snapshot.first().toPromptContext()
                                    client.send(config, prompt, history, memoryContext, selectedImage)
                                }.onSuccess { reply ->
                                    messages.add(ChatLine("assistant", reply.text))
                                    config = store.load()
                                }.onFailure { failure ->
                                    error = failure.message ?: "AI request failed."
                                    if (messages.lastOrNull()?.role == "user") messages.removeAt(messages.lastIndex)
                                }
                                sending = false
                            }
                        }
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = if (input.isNotBlank() && !sending) Color(0xFFD6B56D) else Color(0xFF6E647B))
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeIntelligencePanel() {
    Column(Modifier.fillMaxWidth().padding(top = 26.dp, bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier.size(74.dp).clip(CircleShape)
                .background(Brush.radialGradient(listOf(Color(0x665F3A8A), Color(0x22301B49), Color.Transparent))),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFE2C47D), modifier = Modifier.size(30.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text("STRATEGIC INTELLIGENCE", color = Color(0xFFE4D5B5), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(7.dp))
        Text("Ask. Analyze. Plan.", color = Color(0xFF91859F), fontSize = 12.sp)
    }
}

@Composable
private fun ChatMessage(line: ChatLine) {
    val isUser = line.role == "user"
    Column(Modifier.fillMaxWidth(), horizontalAlignment = if (isUser) Alignment.End else Alignment.Start) {
        if (!isUser) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)) {
                Box(Modifier.size(5.dp).background(Color(0xFFD6B56D), CircleShape))
                Spacer(Modifier.width(6.dp))
                Text("STRATEGY AI", color = Color(0xFFC7A967), fontSize = 9.sp, letterSpacing = 1.1.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        if (!isUser && line.text.startsWith("## Village screenshot report")) {
            ScreenshotReportCard(line.text)
        } else {
            Surface(
                color = if (isUser) Color(0xDD38264F) else Color(0xD9181422),
                shape = RoundedCornerShape(
                    topStart = if (isUser) 17.dp else 5.dp,
                    topEnd = if (isUser) 5.dp else 17.dp,
                    bottomEnd = 17.dp,
                    bottomStart = 17.dp
                ),
                modifier = Modifier.widthIn(max = 340.dp),
                border = BorderStroke(1.dp, if (isUser) Color(0x554F3B68) else Color(0x444D3A65)),
                shadowElevation = 2.dp
            ) {
                Column(Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
                    Text(
                        text = renderChatMarkdown(line.text),
                        color = Color(0xFFF1EDF7),
                        fontSize = 13.sp,
                        lineHeight = 22.sp,
                        style = TextStyle(textDirection = TextDirection.Content, textAlign = TextAlign.Start)
                    )
                }
            }
        }
    }
}


private data class ScreenshotReportLine(
    val text: String,
    val confidence: String? = null,
)

private data class ScreenshotReportSection(
    val title: String,
    val lines: List<ScreenshotReportLine>,
)

private data class ScreenshotReportContent(
    val subtitle: String,
    val sections: List<ScreenshotReportSection>,
)

private fun parseScreenshotReport(source: String): ScreenshotReportContent {
    val normalized = source.replace("\r\n", "\n").replace("\r", "\n")
    val lines = normalized.lines()
    val subtitle = lines.firstOrNull { it.startsWith("**Screenshot type:**") }
        ?.removePrefix("**Screenshot type:**")?.trim().orEmpty()
    val sections = mutableListOf<ScreenshotReportSection>()
    var currentTitle = ""
    var currentLines = mutableListOf<ScreenshotReportLine>()

    fun flushSection() {
        if (currentTitle.isNotBlank()) {
            sections += ScreenshotReportSection(currentTitle, currentLines.toList())
        }
    }

    lines.drop(1).forEach { raw ->
        val line = raw.trim()
        when {
            line.startsWith("### ") -> {
                flushSection()
                currentTitle = line.removePrefix("### ").trim()
                currentLines = mutableListOf()
            }
            line.isBlank() || line.startsWith("**Screenshot type:**") ||
                line.startsWith("_Visual confidence") -> Unit
            else -> {
                val clean = line.removePrefix("- ").removePrefix("* ")
                    .removePrefix("  - ").trim()
                if (clean.isNotBlank()) {
                    val confidence = Regex("""\*\*(High|Medium|Low|Unclear) confidence\*\*""", RegexOption.IGNORE_CASE)
                        .find(clean)?.groupValues?.getOrNull(1)?.lowercase()
                    currentLines += ScreenshotReportLine(
                        text = clean.replace("**", "").replace("_", ""),
                        confidence = confidence,
                    )
                }
            }
        }
    }
    flushSection()
    return ScreenshotReportContent(subtitle, sections)
}

private fun confidenceColor(confidence: String): Color = when (confidence) {
    "high" -> Color(0xFF78C99A)
    "medium" -> Color(0xFFE2C276)
    "low" -> Color(0xFFE3A16F)
    else -> Color(0xFFAA9DBB)
}

@Composable
private fun ConfidencePill(confidence: String) {
    val label = when (confidence) {
        "high" -> "HIGH"
        "medium" -> "MEDIUM"
        "low" -> "LOW"
        else -> "UNCLEAR"
    }
    Surface(
        color = confidenceColor(confidence).copy(alpha = 0.12f),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, confidenceColor(confidence).copy(alpha = 0.45f)),
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
            color = confidenceColor(confidence),
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.55.sp,
        )
    }
}

@Composable
private fun ScreenshotReportCard(source: String) {
    val report = remember(source) { parseScreenshotReport(source) }
    val entrySection = report.sections.firstOrNull {
        it.title.equals("Recommended entry", ignoreCase = true)
    }
    val attackSection = report.sections.firstOrNull {
        it.title.equals("Suggested attack sequence", ignoreCase = true)
    }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xE914101F),
        border = BorderStroke(1.dp, Color(0x887B613D)),
        shadowElevation = 5.dp,
    ) {
        Column(
            Modifier.background(
                Brush.verticalGradient(listOf(Color(0xFF21172F), Color(0xFF110E19), Color(0xFF0D0B14)))
            ).padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(34.dp).clip(RoundedCornerShape(11.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFF6B4A88), Color(0xFF30213F)))),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFE5C77E), modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text("VILLAGE INTELLIGENCE", color = Color(0xFFE7CC8C), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.15.sp)
                    Text("Screenshot analysis", color = Color(0xFFB8AFC4), fontSize = 11.sp)
                }
                Text("AI REPORT", color = Color(0xFF9D8BAE), fontSize = 8.sp, letterSpacing = 0.8.sp)
            }

            if (report.subtitle.isNotBlank()) {
                Text(report.subtitle, color = Color(0xFFF3E7CF), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(Brush.horizontalGradient(listOf(Color.Transparent, Color(0xFF9A7747), Color.Transparent))))

            report.sections.filterNot { it === entrySection || it === attackSection }.forEach { section ->
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(section.title.uppercase(), color = Color(0xFFD9B96F), fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.95.sp)
                    section.lines.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Box(Modifier.padding(top = 6.dp).size(5.dp).background(Color(0xFFD6B56D), CircleShape))
                            Spacer(Modifier.width(8.dp))
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(item.text, color = Color(0xFFE8E1EF), fontSize = 12.sp, lineHeight = 18.sp, style = TextStyle(textDirection = TextDirection.Content))
                                item.confidence?.let { ConfidencePill(it) }
                            }
                        }
                    }
                }
            }

            entrySection?.let { section ->
                Surface(
                    color = Color(0xFF2A2031),
                    shape = RoundedCornerShape(13.dp),
                    border = BorderStroke(1.dp, Color(0x99D6B56D)),
                ) {
                    Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        Text("RECOMMENDED ENTRY", color = Color(0xFFE7CC8C), fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        section.lines.forEach { item ->
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFE2C276), modifier = Modifier.size(15.dp))
                                Spacer(Modifier.width(7.dp))
                                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Text(item.text, color = Color(0xFFFFE9B8), fontSize = 12.sp, lineHeight = 18.sp, fontWeight = FontWeight.SemiBold)
                                    item.confidence?.let { ConfidencePill(it) }
                                }
                            }
                        }
                    }
                }
            }

            attackSection?.let { section ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("ATTACK SEQUENCE", color = Color(0xFFD9B96F), fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.95.sp)
                    section.lines.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Box(
                                Modifier.size(23.dp).clip(CircleShape)
                                    .background(Brush.linearGradient(listOf(Color(0xFF80613C), Color(0xFF392842)))),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text((index + 1).toString(), color = Color(0xFFFFE7AD), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.width(9.dp))
                            Text(item.text, color = Color(0xFFE8E1EF), fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.weight(1f), style = TextStyle(textDirection = TextDirection.Content))
                        }
                    }
                }
            }

            if (report.sections.isEmpty()) {
                Text("The report could not be structured. Review the original analysis before acting.", color = Color(0xFFB8AFC4), fontSize = 12.sp, lineHeight = 18.sp)
            }
            Text(
                "Visual estimates are advisory. Verify uncertain targets before attacking.",
                color = Color(0xFF9D91AA), fontSize = 9.sp, lineHeight = 14.sp,
            )
        }
    }
}

@Composable
private fun ThinkingIndicator() {
    val transition = rememberInfiniteTransition(label = "thinking")
    val pulse by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(850, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "thinkingPulse"
    )
    Row(Modifier.padding(start = 6.dp, top = 3.dp, bottom = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(7.dp).background(Color(0xFFD6B56D).copy(alpha = pulse), CircleShape))
        Spacer(Modifier.width(8.dp))
        Text("STRATEGY CORE ACTIVE", color = Color(0xFFC7A967), fontSize = 9.sp, letterSpacing = 1.1.sp)
        Spacer(Modifier.width(7.dp))
        Text("Analyzing", color = Color(0xFF92869F), fontSize = 10.sp)
    }
}

@Composable
private fun AnimatedIntelligenceBackdrop(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "intelligenceBackdrop")
    val phase by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(11000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "backdropPhase"
    )
    val glow by transition.animateFloat(
        initialValue = 0.22f, targetValue = 0.48f,
        animationSpec = infiniteRepeatable(tween(4200, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "backdropGlow"
    )
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        drawRect(brush = Brush.verticalGradient(listOf(Color(0xFF090711), Color(0xFF0D0917), Color(0xFF080610))))
        val orbCenter = Offset(w * (0.18f + phase * 0.12f), h * (0.34f + phase * 0.08f))
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x665D368B).copy(alpha = glow), Color(0x223B245B), Color.Transparent),
                center = orbCenter, radius = w * 0.62f
            ),
            radius = w * 0.62f, center = orbCenter
        )
        val secondCenter = Offset(w * (0.92f - phase * 0.1f), h * 0.78f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x33A47B3F).copy(alpha = glow), Color(0x113D2B50), Color.Transparent),
                center = secondCenter, radius = w * 0.45f
            ),
            radius = w * 0.45f, center = secondCenter
        )
        for (index in 0..3) {
            drawCircle(
                color = Color(0x18A88AD0).copy(alpha = glow),
                radius = w * (0.18f + index * 0.085f),
                center = Offset(w * 0.82f, h * 0.16f),
                style = Stroke(width = 0.7.dp.toPx())
            )
        }
        for (index in 0..24) {
            val x = (index * 73f) % w
            val y = (index * 137f) % h
            val twinkle = 0.18f + (index % 4) * 0.09f
            drawCircle(
                color = if (index % 5 == 0) Color(0xFFD6B56D).copy(alpha = twinkle * glow * 2f)
                else Color(0xFFBDA5DF).copy(alpha = twinkle * glow),
                radius = if (index % 5 == 0) 1.5.dp.toPx() else 0.8.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}

private fun renderChatMarkdown(source: String): AnnotatedString = buildAnnotatedString {
    val lines = source.replace("\r\n", "\n").replace("\r", "\n").lines()
    lines.forEachIndexed { index, rawLine ->
        val line = rawLine.trimEnd()
        val heading = when {
            line.startsWith("### ") -> line.removePrefix("### ")
            line.startsWith("## ") -> line.removePrefix("## ")
            line.startsWith("# ") -> line.removePrefix("# ")
            else -> null
        }
        if (heading != null) {
            pushStyle(SpanStyle(color = Color(0xFFE3C77F), fontWeight = FontWeight.Bold))
            append(heading)
            pop()
        } else {
            val bullet = line.startsWith("- ") || line.startsWith("* ")
            val numbered = Regex("^\\d+[.)]\\s+").find(line)
            if (bullet) {
                pushStyle(SpanStyle(color = Color(0xFFD6B56D), fontWeight = FontWeight.Bold))
                append("• ")
                pop()
                appendInlineMarkdown(line.drop(2))
            } else if (numbered != null) {
                pushStyle(SpanStyle(color = Color(0xFFD6B56D), fontWeight = FontWeight.Bold))
                append(numbered.value)
                pop()
                appendInlineMarkdown(line.drop(numbered.value.length))
            } else {
                appendInlineMarkdown(line)
            }
        }
        if (index != lines.lastIndex) append("\n")
    }
}

private fun AnnotatedString.Builder.appendInlineMarkdown(line: String) {
    val tokenPattern = Regex("(\\*\\*.+?\\*\\*|\\u0060[^\\u0060]+\\u0060)")
    var cursor = 0
    tokenPattern.findAll(line).forEach { match ->
        append(line.substring(cursor, match.range.first))
        val token = match.value
        val isCode = token.startsWith("\u0060")
        val value = if (isCode) token.trim('\u0060') else token.removePrefix("**").removeSuffix("**")
        pushStyle(
            SpanStyle(
                color = if (isCode) Color(0xFFE5C982) else Color(0xFFF4DDA5),
                fontWeight = if (isCode) FontWeight.Medium else FontWeight.Bold
            )
        )
        append(value)
        pop()
        cursor = match.range.last + 1
    }
    if (cursor < line.length) append(line.substring(cursor))
}
