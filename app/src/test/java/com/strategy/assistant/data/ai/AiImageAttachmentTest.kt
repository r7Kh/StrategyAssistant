package com.strategy.assistant.data.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class AiImageAttachmentTest {
    @Test
    fun acceptsSupportedImageTypes() {
        listOf("image/jpeg", "image/png", "image/webp").forEach { mime ->
            assertEquals(mime, AiImageAttachment(byteArrayOf(1), mime).mimeType)
        }
    }

    @Test
    fun rejectsUnsupportedImageType() {
        assertThrows(IllegalArgumentException::class.java) {
            AiImageAttachment(byteArrayOf(1), "image/gif")
        }
    }

    @Test
    fun rejectsEmptyImage() {
        assertThrows(IllegalArgumentException::class.java) {
            AiImageAttachment(byteArrayOf(), "image/png")
        }
    }

    @Test
    fun rejectsImagesLargerThanEightMegabytes() {
        assertThrows(IllegalArgumentException::class.java) {
            AiImageAttachment(ByteArray(AiImageAttachment.MAX_BYTES + 1), "image/jpeg")
        }
    }
}
