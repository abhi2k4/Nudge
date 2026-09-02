package com.nudge.domain.repository

import com.nudge.core.common.NudgeResult
import com.nudge.core.model.Intent
import com.nudge.core.model.IntentStatus
import kotlinx.coroutines.flow.Flow

interface IntentRepository {

    fun observeAll(): Flow<List<Intent>>

    fun observeByStatus(status: IntentStatus): Flow<List<Intent>>

    suspend fun getById(id: String): Intent?

    suspend fun getByCaptureId(captureId: String): List<Intent>

    suspend fun save(intent: Intent): NudgeResult<Intent>

    suspend fun updateStatus(id: String, status: IntentStatus): NudgeResult<Unit>

    suspend fun delete(id: String): NudgeResult<Unit>
}
