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
 * Step 1 of the processing pipeline.
 *
 * Picks up a new capture and marks it as QUEUED, then hands off to
 * [PreprocessWorker] via chained WorkManager requests.
 *
 * Input data key: [KEY_CAPTURE_ID]
 */
@HiltWorker
class CaptureWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val captureRepository: CaptureRepository,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val captureId = inputData.getString(KEY_CAPTURE_ID)
            ?: return Result.failure().also {
                NudgeLogger.error(TAG, "CaptureWorker: missing captureId in input data")
            }

        NudgeLogger.stageStart("CaptureWorker", captureId)

        return try {
            captureRepository.updateProcessingStatus(captureId, ProcessingStatus.QUEUED)
            NudgeLogger.stageEnd("CaptureWorker", captureId, 0)
            Result.success(inputData)
        } catch (e: Exception) {
            NudgeLogger.stageFailed("CaptureWorker", captureId, e)
            if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val KEY_CAPTURE_ID = "capture_id"
        const val WORK_NAME_PREFIX = "capture_worker_"
        private const val TAG = "CaptureWorker"
        private const val MAX_RETRIES = 3
    }
}
