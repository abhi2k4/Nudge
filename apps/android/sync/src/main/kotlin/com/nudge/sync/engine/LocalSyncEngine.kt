package com.nudge.sync.engine

import com.nudge.core.logging.NudgeLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Local-only SyncEngine implementation.
 *
 * This implementation stores all data locally and never contacts a remote
 * backend. It is the default for the hackathon foundation phase.
 *
 * Replace with [SupabaseSyncEngine] once Supabase credentials are configured
 * and the remote data layer is wired up.
 */
class LocalSyncEngine @Inject constructor() : SyncEngine {

    private val _state = MutableStateFlow(SyncEngineState.IDLE)

    override fun observeSyncState(): Flow<SyncEngineState> = _state.asStateFlow()

    override suspend fun sync(): SyncResult {
        NudgeLogger.info(TAG, "LocalSyncEngine.sync() called — no-op (local-only mode)")
        return SyncResult.Success(
            uploadedCount = 0,
            downloadedCount = 0,
            conflictCount = 0,
        )
    }

    override suspend fun cancel() {
        NudgeLogger.debug(TAG, "LocalSyncEngine.cancel() — nothing to cancel")
    }

    private companion object {
        const val TAG = "LocalSyncEngine"
    }
}
