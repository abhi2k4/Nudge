package com.nudge.domain.repository

import com.nudge.core.common.NudgeResult
import com.nudge.core.model.Capture
import com.nudge.core.model.CaptureType
import com.nudge.core.model.ProcessingStatus
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for [Capture] entities.
 *
 * Implementations must:
 *  - Always persist locally before returning success.
 *  - Never throw exceptions across layer boundaries (use [NudgeResult]).
 *  - Never make network connectivity a prerequisite for [save].
 */
interface CaptureRepository {

    /** Observe all captures, ordered newest-first. */
    fun observeAll(): Flow<List<Capture>>

    /** Retrieve a single capture by ID. Returns null if not found. */
    suspend fun getById(id: String): Capture?

    /**
     * Persist a new capture locally.
     * This must succeed regardless of AI or network availability.
     */
    suspend fun save(capture: Capture): NudgeResult<Capture>

    /** Update an existing capture (e.g. after processing updates its status). */
    suspend fun update(capture: Capture): NudgeResult<Unit>

    /** Delete a capture and cascade-delete related entities. */
    suspend fun delete(id: String): NudgeResult<Unit>

    /** Returns captures that are waiting for AI processing. */
    suspend fun getPendingProcessing(): List<Capture>

    /** Updates only the processing status field. */
    suspend fun updateProcessingStatus(id: String, status: ProcessingStatus): NudgeResult<Unit>
}
