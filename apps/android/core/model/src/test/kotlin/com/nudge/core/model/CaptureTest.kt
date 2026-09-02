package com.nudge.core.model

import org.junit.Assert.*
import org.junit.Test

class CaptureTest {

    @Test
    fun `capture defaults to PENDING processing status`() {
        val capture = makeCapture()
        assertEquals(ProcessingStatus.PENDING, capture.processingStatus)
    }

    @Test
    fun `capture defaults to LOCAL_ONLY sync status`() {
        val capture = makeCapture()
        assertEquals(SyncStatus.LOCAL_ONLY, capture.syncStatus)
    }

    @Test
    fun `capture metadata is empty by default`() {
        val capture = makeCapture()
        assertTrue(capture.metadata.isEmpty())
    }

    @Test
    fun `capture with metadata preserves all entries`() {
        val meta = mapOf("key1" to "value1", "key2" to "value2")
        val capture = makeCapture(metadata = meta)
        assertEquals(meta, capture.metadata)
    }

    @Test
    fun `capture id must be set explicitly`() {
        val capture = makeCapture(id = "test-id-123")
        assertEquals("test-id-123", capture.id)
    }

    private fun makeCapture(
        id: String = "default-id",
        type: CaptureType = CaptureType.TEXT,
        metadata: Map<String, String> = emptyMap(),
    ) = Capture(
        id = id,
        type = type,
        createdAt = 1_700_000_000_000L,
        source = "test",
        text = "Test capture content",
        metadata = metadata,
    )
}
