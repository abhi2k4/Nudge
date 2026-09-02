package com.nudge.core.model

import kotlinx.serialization.Serializable

// ─────────────────────────────────────────────────────────────────────────────
// Action — a concrete, schedulable task derived from an intent.
// ─────────────────────────────────────────────────────────────────────────────

enum class ActionType {
    SET_REMINDER,
    CREATE_CALENDAR_EVENT,
    SEND_MESSAGE,
    OPEN_LINK,
    ADD_TO_LIST,
    SEARCH,
    CUSTOM,
}

enum class ActionStatus {
    PENDING,
    SCHEDULED,
    COMPLETED,
    CANCELLED,
    FAILED,
}

/**
 * A concrete action generated from a user intent.
 *
 * Actions are the executable output of the intent pipeline.
 * They can be scheduled, executed, or manually completed by the user.
 */
@Serializable
data class Action(
    val id: String,
    val intentId: String,
    val type: ActionType,
    val title: String,
    val description: String? = null,
    val scheduledAt: Long? = null,              // epoch millis; null = not scheduled
    val status: ActionStatus = ActionStatus.PENDING,
    val createdAt: Long,
    val payload: Map<String, String> = emptyMap(), // type-specific action data
)
