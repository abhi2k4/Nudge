package com.nudge.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contexts")
data class NudgeContextEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val embeddingJson: String,              // JSON-serialised FloatArray — placeholder for pgvector migration
)
