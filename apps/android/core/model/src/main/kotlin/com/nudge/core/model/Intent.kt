package com.nudge.core.model

import kotlinx.serialization.Serializable

// ─────────────────────────────────────────────────────────────────────────────
// Intent — the extracted meaning behind a capture.
// ─────────────────────────────────────────────────────────────────────────────

/**
 * The category of action or commitment the user appears to intend.
 * Derived by the on-device AI from the capture content.
 */
enum class IntentType {
    REMINDER,
    TASK,
    NOTE,
    RESEARCH,
    BOOKING,
    PURCHASE,
    MEETING,
    UNKNOWN,
}

/**
 * Lifecycle status of an extracted intent.
 */
enum class IntentStatus {
    /** Newly extracted; not yet reviewed or acted on. */
    NEW,

    /** User has acknowledged this intent. */
    ACKNOWLEDGED,

    /** An action has been created from this intent. */
    ACTIONED,

    /** User has explicitly dismissed this intent. */
    DISMISSED,

    /** Intent was completed (task done, reminder passed, etc.). */
    COMPLETED,
}

/**
 * A structured representation of the user's intent behind a capture.
 *
 * Intent is always derived by the AI layer and validated before
 * being persisted. It is never written directly from raw LLM output.
 *
 * @param confidence AI confidence in this classification (0.0–1.0).
 * @param deadline Human-readable deadline string (e.g. "this weekend").
 *                 Normalisation to a concrete timestamp is a next-phase task.
 */
@Serializable
data class Intent(
    val id: String,
    val captureId: String,
    val type: IntentType,
    val title: String,
    val description: String? = null,
    val project: String? = null,
    val deadline: String? = null,
    val confidence: Float,
    val status: IntentStatus = IntentStatus.NEW,
    val createdAt: Long,                        // epoch millis
)
