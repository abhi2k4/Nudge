package com.nudge.data.repository

import com.nudge.domain.repository.IntentRepository
import com.nudge.core.common.NudgeResult
import com.nudge.core.model.Intent
import com.nudge.core.model.IntentStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntentRepositoryImpl @Inject constructor() : IntentRepository {
    override fun observeAll(): Flow<List<Intent>> = kotlinx.coroutines.flow.emptyFlow()
    override fun observeByStatus(status: IntentStatus): Flow<List<Intent>> = kotlinx.coroutines.flow.emptyFlow()
    override suspend fun getById(id: String): Intent? = null
    override suspend fun getByCaptureId(captureId: String): List<Intent> = emptyList()
    override suspend fun save(intent: Intent): NudgeResult<Intent> = NudgeResult.Success(intent)
    override suspend fun updateStatus(id: String, status: IntentStatus): NudgeResult<Unit> = NudgeResult.Success(Unit)
    override suspend fun delete(id: String): NudgeResult<Unit> = NudgeResult.Success(Unit)
}
