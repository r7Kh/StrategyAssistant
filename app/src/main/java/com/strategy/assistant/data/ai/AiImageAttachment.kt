package com.strategy.assistant.data.ai

/**
 * A transient image supplied for one AI request. The bytes are not persisted by this type.
 */
data class AiImageAttachment(
    val bytes: ByteArray,
    val mimeType: String
) {
    init {
        require(mimeType in SUPPORTED_MIME_TYPES) { "Use a JPG, PNG, or WebP image." }
        require(bytes.isNotEmpty() && bytes.size <= MAX_BYTES) { "Image must be smaller than 8 MB." }
    }

    companion object {
        const val MAX_BYTES = 8 * 1024 * 1024
        val SUPPORTED_MIME_TYPES = setOf("image/jpeg", "image/png", "image/webp")
    }
}
