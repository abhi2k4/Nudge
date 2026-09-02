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
 * Step 4 — Context Resolution.
 *
 * Finds related captures, contexts, and prior intents using embedding
 * similarity and relationship traversal.
 *
 * STATUS: Skeleton — context resolution logic pending embedding implementation.
 */
@HiltWorker
class ContextWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val captureRepository: CaptureRepository,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val captureId = inputData.getString(CaptureWorker.KEY_CAPTURE_ID)
            ?: return Result.failure().also {
                NudgeLogger.error(TAG, "ContextWorker: missing captureId")
            }

        NudgeLogger.stageStart("ContextWorker", captureId)

        return try {
            captureRepository.updateProcessingStatus(captureId, ProcessingStatus.RESOLVING_CONTEXT)

            // TODO: Implement context resolution when LocalEmbeddingProvider is ready:
            //  1. Embed the capture text
            //  2. Find similar captures via cosine similarity
            //  3. Create/update NudgeContext and Relationship records

            NudgeLogger.stageEnd("ContextWorker", captureId, 0)
            Result.success(inputData)
        } catch (e: Exception) {
            NudgeLogger.stageFailed("ContextWorker", captureId, e)
            if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure()
        }
    }

    companion object {
        private const val TAG = "ContextWorker"
        private const val MAX_RETRIES = 3
    }
}
