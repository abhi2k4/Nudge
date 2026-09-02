package com.nudge.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nudge.core.model.IntentStatus
import com.nudge.core.model.IntentType

@Entity(
    tableName = "intents",
    foreignKeys = [
        ForeignKey(
            entity = CaptureEntity::class,
            parentColumns = ["id"],
            childColumns = ["captureId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("captureId")],
)
@TypeConverters(IntentConverters::class)
data class IntentEntity(
    @PrimaryKey val id: String,
    val captureId: String,
    val type: IntentType,
    val title: String,
    val description: String?,
    val project: String?,
    val deadline: String?,
    val confidence: Float,
    val status: IntentStatus,
    val createdAt: Long,
)

class IntentConverters {
    @TypeConverter fun fromIntentType(value: IntentType): String = value.name
    @TypeConverter fun toIntentType(value: String): IntentType = IntentType.valueOf(value)
    @TypeConverter fun fromIntentStatus(value: IntentStatus): String = value.name
    @TypeConverter fun toIntentStatus(value: String): IntentStatus = IntentStatus.valueOf(value)
}
