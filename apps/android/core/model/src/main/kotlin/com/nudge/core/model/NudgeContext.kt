package com.nudge.core.model

import kotlinx.serialization.Serializable

// ─────────────────────────────────────────────────────────────────────────────
// NudgeContext — a semantic cluster of related captures, intents, and actions.
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A thematic context grouping related information.
 *
 * Contexts are derived by the context engine from semantic similarity
 * and explicit relationships between captures and intents.
 *
 * Named "NudgeContext" to avoid clashing with Android's [android.content.Context].
 */
@Serializable
data class NudgeContext(
    val id: String,
    val title: String,
    val description: String? = null,
    val createdAt: Long,                        // epoch millis
    val updatedAt: Long,
    val embedding: List<Float> = emptyList(),   // cached embedding vector for search
)
