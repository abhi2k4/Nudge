package com.nudge.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nudge.core.logging.NudgeLogger
import com.nudge.core.model.ProcessingStatus
import com.nudge.domain.repository.CaptureRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Step 5 — Action Creation.
 *
 * Converts high-confidence intents into schedulable [Action] records.
 *
 * STATUS: Skeleton — action creation logic pending intent review integration.
 */
@HiltWorker
class ActionWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val captureRepository: CaptureRepository,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val captureId = inputData.getString(CaptureWorker.KEY_CAPTURE_ID)
            ?: return Result.failure().also {
                NudgeLogger.error(TAG, "ActionWorker: missing captureId")
            }

        NudgeLogger.stageStart("ActionWorker", captureId)

        return try {
            // TODO: For each high-confidence Intent linked to captureId:
            //  1. Determine the appropriate ActionType
            //  2. Create an Action record
            //  3. Schedule via WorkManager / AlarmManager if scheduledAt is set

            captureRepository.updateProcessingStatus(captureId, ProcessingStatus.COMPLETE)
            NudgeLogger.stageEnd("ActionWorker", captureId, 0)
            Result.success(inputData)
        } catch (e: Exception) {
            NudgeLogger.stageFailed("ActionWorker", captureId, e)
            if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure()
        }
    }

    companion object {
        private const val TAG = "ActionWorker"
        private const val MAX_RETRIES = 3
    }
}
