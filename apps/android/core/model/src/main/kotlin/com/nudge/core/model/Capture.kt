package com.nudge.core.model

import kotlinx.serialization.Serializable
import java.time.Instant

// ─────────────────────────────────────────────────────────────────────────────
// Capture — the primary unit of information in NUDGE.
// Everything the user creates flows through this model first.
// ─────────────────────────────────────────────────────────────────────────────

/**
 * The type of content the user has captured.
 */
enum class CaptureType {
    VOICE,
    SCREENSHOT,
    PHOTO,
    TEXT,
    LINK,
    DOCUMENT,
}

/**
 * Tracks where a capture is in the local AI processing pipeline.
 * Captures are always persisted before processing begins.
 */
enum class ProcessingStatus {
    /** Capture saved to disk; not yet queued for AI processing. */
    PENDING,

    /** Capture enqueued in the WorkManager processing pipeline. */
    QUEUED,

    /** Pre-processing (resize, transcription, OCR) is in progress. */
    PREPROCESSING,

    /** AI intent extraction is running. */
    EXTRACTING_INTENT,

    /** Context linking is running. */
    RESOLVING_CONTEXT,

    /** All processing complete. */
    COMPLETE,

    /** Processing failed. Retry may be scheduled. */
    FAILED,
}

/**
 * Tracks whether a capture has been synced to the remote backend.
 * Sync is always optional and never blocks local capture.
 */
enum class SyncStatus {
    LOCAL_ONLY,
    SYNC_PENDING,
    SYNCED,
    CONFLICT,
    FAILED,
}

/**
 * A single user capture. This is the entry point for all information
 * flowing through the NUDGE system.
 *
 * Captures are immutable after creation; mutations produce new instances.
 */
@Serializable
data class Capture(
    val id: String,
    val type: CaptureType,
    val createdAt: Long,                        // epoch millis
    val source: String,                         // e.g. "android_share", "manual", "widget"
    val contentUri: String? = null,             // file:// or content:// URI for media
    val text: String? = null,                   // raw text, transcript, or link URL
    val metadata: Map<String, String> = emptyMap(), // flexible bag for type-specific extras
    val processingStatus: ProcessingStatus = ProcessingStatus.PENDING,
    val syncStatus: SyncStatus = SyncStatus.LOCAL_ONLY,
)
