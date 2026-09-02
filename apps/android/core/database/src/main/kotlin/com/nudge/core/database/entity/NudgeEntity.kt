package com.nudge.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nudge.core.model.NudgeStatus

@Entity(
    tableName = "nudges",
    foreignKeys = [
        ForeignKey(
            entity = IntentEntity::class,
            parentColumns = ["id"],
            childColumns = ["intentId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("intentId")],
)
@TypeConverters(NudgeConverters::class)
data class NudgeEntity(
    @PrimaryKey val id: String,
    val intentId: String,
    val message: String,
    val scheduledAt: Long,
    val shownAt: Long?,
    val status: NudgeStatus,
)

class NudgeConverters {
    @TypeConverter fun fromNudgeStatus(value: NudgeStatus): String = value.name
    @TypeConverter fun toNudgeStatus(value: String): NudgeStatus = NudgeStatus.valueOf(value)
}
