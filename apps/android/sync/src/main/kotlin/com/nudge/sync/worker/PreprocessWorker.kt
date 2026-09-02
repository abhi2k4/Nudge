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
 * Step 2 — Pre-processing.
 *
 * Handles media pre-processing before AI inference:
 *  - VOICE: triggers ASR transcription
 *  - SCREENSHOT/PHOTO: image description extraction (TODO)
 *  - TEXT/LINK: normalisation
 *
 * On completion, passes the captureId to [IntentWorker].
 */
@HiltWorker
class PreprocessWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val captureRepository: CaptureRepository,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val captureId = inputData.getString(CaptureWorker.KEY_CAPTURE_ID)
            ?: return Result.failure().also {
                NudgeLogger.error(TAG, "PreprocessWorker: missing captureId")
            }

        NudgeLogger.stageStart("PreprocessWorker", captureId)

        return try {
            captureRepository.updateProcessingStatus(captureId, ProcessingStatus.PREPROCESSING)

            // TODO: Implement per-type preprocessing:
            //   - VOICE -> AndroidSpeechRecognizer.transcribe()
            //   - SCREENSHOT/PHOTO -> image-to-text (next phase)
            //   - LINK -> fetch title/description
            //   - TEXT/DOCUMENT -> extract + clean text

            NudgeLogger.stageEnd("PreprocessWorker", captureId, 0)
            Result.success(inputData)
        } catch (e: Exception) {
            NudgeLogger.stageFailed("PreprocessWorker", captureId, e)
            if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure()
        }
    }

    companion object {
        private const val TAG = "PreprocessWorker"
        private const val MAX_RETRIES = 3
    }
}
