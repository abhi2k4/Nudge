package com.nudge.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nudge.core.model.ActionStatus
import com.nudge.core.model.ActionType

@Entity(
    tableName = "actions",
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
@TypeConverters(ActionConverters::class)
data class ActionEntity(
    @PrimaryKey val id: String,
    val intentId: String,
    val type: ActionType,
    val title: String,
    val description: String?,
    val scheduledAt: Long?,
    val status: ActionStatus,
    val createdAt: Long,
    val payloadJson: String,                // JSON-serialised Map<String, String>
)

class ActionConverters {
    @TypeConverter fun fromActionType(value: ActionType): String = value.name
    @TypeConverter fun toActionType(value: String): ActionType = ActionType.valueOf(value)
    @TypeConverter fun fromActionStatus(value: ActionStatus): String = value.name
    @TypeConverter fun toActionStatus(value: String): ActionStatus = ActionStatus.valueOf(value)
}
