package com.nudge.sync.engine

import com.nudge.core.model.SyncStatus
import kotlinx.coroutines.flow.Flow

/**
 * The SyncEngine is responsible for synchronising local data with the
 * remote Supabase backend.
 *
 * Implementation contract:
 *  - Local state is always the source of truth.
 *  - Sync must never block capture or local AI processing.
 *  - Sync state is observable via [observeSyncState].
 *  - Implementations must handle network failures gracefully.
 */
interface SyncEngine {

    /** The current sync state as a reactive Flow. */
    fun observeSyncState(): Flow<SyncEngineState>

    /**
     * Triggers a full sync cycle:
     *  1. Upload pending local changes.
     *  2. Fetch remote changes.
     *  3. Resolve any conflicts.
     */
    suspend fun sync(): SyncResult

    /** Cancels any in-progress sync. */
    suspend fun cancel()
}

/**
 * Describes the overall state of the sync engine.
 */
enum class SyncEngineState {
    IDLE,
    SYNCING,
    CONFLICT,
    ERROR,
}

/**
 * The outcome of a sync cycle.
 */
sealed class SyncResult {
    data class Success(
        val uploadedCount: Int,
        val downloadedCount: Int,
        val conflictCount: Int,
    ) : SyncResult()

    data class Failure(
        val message: String,
        val cause: Throwable? = null,
    ) : SyncResult()

    object Cancelled : SyncResult()
}
