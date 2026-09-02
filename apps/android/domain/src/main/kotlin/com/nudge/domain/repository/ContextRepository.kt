package com.nudge.domain.repository

import com.nudge.core.common.NudgeResult
import com.nudge.core.model.NudgeContext
import kotlinx.coroutines.flow.Flow

interface ContextRepository {

    fun observeAll(): Flow<List<NudgeContext>>

    suspend fun getById(id: String): NudgeContext?

    suspend fun getRecent(limit: Int = 20): List<NudgeContext>

    suspend fun save(context: NudgeContext): NudgeResult<NudgeContext>

    suspend fun update(context: NudgeContext): NudgeResult<Unit>

    suspend fun delete(id: String): NudgeResult<Unit>
}
