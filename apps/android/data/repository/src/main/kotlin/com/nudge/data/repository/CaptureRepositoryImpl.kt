package com.nudge.data.repository

import com.nudge.core.common.NudgeResult
import com.nudge.core.common.safeCall
import com.nudge.core.model.Capture
import com.nudge.core.model.ProcessingStatus
import com.nudge.data.local.CaptureLocalDataSource
import com.nudge.domain.repository.CaptureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository implementation for [Capture] entities.
 *
 * Routes all operations through the local data source.
 * Remote sync is handled separately by [SyncWorker] — never inline.
 */
class CaptureRepositoryImpl @Inject constructor(
    private val localDataSource: CaptureLocalDataSource,
) : CaptureRepository {

    override fun observeAll(): Flow<List<Capture>> =
        localDataSource.observeAll()

    override suspend fun getById(id: String): Capture? =
        localDataSource.getById(id)

    override suspend fun save(capture: Capture): NudgeResult<Capture> = safeCall {
        localDataSource.save(capture)
        capture
    }

    override suspend fun update(capture: Capture): NudgeResult<Unit> = safeCall {
        localDataSource.update(capture)
    }

    override suspend fun delete(id: String): NudgeResult<Unit> = safeCall {
        // TODO: implement Room delete — requires a query method in CaptureDao
    }

    override suspend fun getPendingProcessing(): List<Capture> =
        localDataSource.getPendingProcessing()

    override suspend fun updateProcessingStatus(
        id: String,
        status: ProcessingStatus,
    ): NudgeResult<Unit> = safeCall {
        localDataSource.updateProcessingStatus(id, status)
    }
}
