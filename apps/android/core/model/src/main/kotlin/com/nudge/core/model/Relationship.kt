package com.nudge.core.model

import kotlinx.serialization.Serializable

// ─────────────────────────────────────────────────────────────────────────────
// Relationship — a directed edge between two entities in the NUDGE graph.
// ─────────────────────────────────────────────────────────────────────────────

/**
 * The nature of a relationship between two NUDGE entities.
 */
enum class RelationshipType {
    /** Two items share semantic content (similar topics/themes). */
    SEMANTIC_SIMILAR,

    /** One item explicitly refers to another. */
    REFERENCES,

    /** Two items belong to the same user-defined project or context. */
    SAME_PROJECT,

    /** One item follows up on another. */
    FOLLOWS_UP,

    /** One item duplicates another. */
    DUPLICATE,
}

/**
 * A directional relationship between two NUDGE entities.
 *
 * [sourceId] and [targetId] can refer to any entity type (Capture, Intent,
 * Action, NudgeContext). Entity type resolution is handled at the repository
 * level.
 *
 * @param confidence AI confidence in this relationship (0.0–1.0).
 */
@Serializable
data class Relationship(
    val id: String,
    val sourceId: String,
    val targetId: String,
    val type: RelationshipType,
    val confidence: Float,
    val createdAt: Long,                        // epoch millis
)
