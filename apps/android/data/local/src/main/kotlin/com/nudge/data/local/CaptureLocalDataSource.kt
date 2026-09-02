package com.nudge.data.local

import com.nudge.core.database.dao.CaptureDao
import com.nudge.core.database.entity.CaptureEntity
import com.nudge.core.model.Capture
import com.nudge.core.model.CaptureType
import com.nudge.core.model.ProcessingStatus
import com.nudge.core.model.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Local data source for [Capture] entities backed by Room.
 *
 * Handles all mapping between [CaptureEntity] (Room) and [Capture] (domain).
 * This class is intentionally thin — business logic belongs in use cases.
 */
class CaptureLocalDataSource @Inject constructor(
    private val captureDao: CaptureDao,
) {
    private val json = Json { ignoreUnknownKeys = true }

    fun observeAll(): Flow<List<Capture>> =
        captureDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    suspend fun getById(id: String): Capture? =
        captureDao.getById(id)?.toDomain()

    suspend fun save(capture: Capture) =
        captureDao.insert(capture.toEntity())

    suspend fun update(capture: Capture) =
        captureDao.update(capture.toEntity())

    suspend fun getPendingProcessing(): List<Capture> =
        captureDao.getByProcessingStatus(ProcessingStatus.PENDING).map { it.toDomain() }

    suspend fun updateProcessingStatus(id: String, status: ProcessingStatus) =
        captureDao.updateProcessingStatus(id, status)

    suspend fun updateSyncStatus(id: String, status: SyncStatus) =
        captureDao.updateSyncStatus(id, status)

    // ── Mapping ──────────────────────────────────────────────────────────────

    private fun CaptureEntity.toDomain(): Capture = Capture(
        id = id,
        type = type,
        createdAt = createdAt,
        source = source,
        contentUri = contentUri,
        text = text,
        metadata = runCatching {
            json.decodeFromString<Map<String, String>>(metadataJson)
        }.getOrDefault(emptyMap()),
        processingStatus = processingStatus,
        syncStatus = syncStatus,
    )

    private fun Capture.toEntity(): CaptureEntity = CaptureEntity(
        id = id,
        type = type,
        createdAt = createdAt,
        source = source,
        contentUri = contentUri,
        text = text,
        metadataJson = json.encodeToString(metadata),
        processingStatus = processingStatus,
        syncStatus = syncStatus,
    )
}
