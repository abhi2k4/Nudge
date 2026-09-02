package com.nudge.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nudge.core.model.RelationshipType

@Entity(
    tableName = "relationships",
    indices = [Index("sourceId"), Index("targetId")],
)
@TypeConverters(RelationshipConverters::class)
data class RelationshipEntity(
    @PrimaryKey val id: String,
    val sourceId: String,
    val targetId: String,
    val type: RelationshipType,
    val confidence: Float,
    val createdAt: Long,
)

class RelationshipConverters {
    @TypeConverter fun fromRelationshipType(value: RelationshipType): String = value.name
    @TypeConverter fun toRelationshipType(value: String): RelationshipType = RelationshipType.valueOf(value)
}
