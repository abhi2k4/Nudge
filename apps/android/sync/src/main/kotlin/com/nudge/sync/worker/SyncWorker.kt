package com.nudge.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nudge.core.logging.NudgeLogger
import com.nudge.sync.engine.SyncEngine
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Step 6 (optional) — Remote sync.
 *
 * Uploads pending local changes to Supabase.
 * This worker only runs when:
 *  - Network is available (WorkManager Constraints enforced)
 *  - User has configured sync (Supabase credentials present)
 *
 * Failure here does NOT roll back the local capture — it just stays
 * in SYNC_PENDING until the next sync cycle.
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val syncEngine: SyncEngine,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        NudgeLogger.info(TAG, "SyncWorker.doWork() triggered")

        return try {
            val result = syncEngine.sync()
            NudgeLogger.info(TAG, "SyncWorker complete: $result")
            Result.success()
        } catch (e: Exception) {
            NudgeLogger.error(TAG, "SyncWorker failed", e)
            if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val WORK_NAME = "nudge_sync_worker"
        private const val TAG = "SyncWorker"
        private const val MAX_RETRIES = 3
    }
}
