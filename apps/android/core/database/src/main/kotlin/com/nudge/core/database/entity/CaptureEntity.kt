package com.nudge.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nudge.core.model.CaptureType
import com.nudge.core.model.ProcessingStatus
import com.nudge.core.model.SyncStatus

@Entity(tableName = "captures")
@TypeConverters(CaptureConverters::class)
data class CaptureEntity(
    @PrimaryKey val id: String,
    val type: CaptureType,
    val createdAt: Long,
    val source: String,
    val contentUri: String?,
    val text: String?,
    val metadataJson: String,               // JSON-serialised Map<String, String>
    val processingStatus: ProcessingStatus,
    val syncStatus: SyncStatus,
)

class CaptureConverters {
    @TypeConverter fun fromCaptureType(value: CaptureType): String = value.name
    @TypeConverter fun toCaptureType(value: String): CaptureType = CaptureType.valueOf(value)
    @TypeConverter fun fromProcessingStatus(value: ProcessingStatus): String = value.name
    @TypeConverter fun toProcessingStatus(value: String): ProcessingStatus = ProcessingStatus.valueOf(value)
    @TypeConverter fun fromSyncStatus(value: SyncStatus): String = value.name
    @TypeConverter fun toSyncStatus(value: String): SyncStatus = SyncStatus.valueOf(value)
}
