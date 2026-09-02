package com.nudge.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nudge.ai.orchestration.AIOrchestrator
import com.nudge.core.common.generateId
import com.nudge.core.common.nowMillis
import com.nudge.core.logging.NudgeLogger
import com.nudge.core.model.IntentStatus
import com.nudge.core.model.IntentType
import com.nudge.core.model.ProcessingStatus
import com.nudge.domain.repository.CaptureRepository
import com.nudge.domain.repository.IntentRepository
import com.nudge.core.model.Intent
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Step 3 — Intent Extraction.
 *
 * Runs on-device AI to extract a structured [Intent] from the capture.
 * Validates output before persisting — never writes raw LLM text to DB.
 */
@HiltWorker
class IntentWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val captureRepository: CaptureRepository,
    private val intentRepository: IntentRepository,
    private val aiOrchestrator: AIOrchestrator,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val captureId = inputData.getString(CaptureWorker.KEY_CAPTURE_ID)
            ?: return Result.failure().also {
                NudgeLogger.error(TAG, "IntentWorker: missing captureId")
            }

        NudgeLogger.stageStart("IntentWorker", captureId)

        val capture = captureRepository.getById(captureId)
            ?: return Result.failure().also {
                NudgeLogger.error(TAG, "IntentWorker: capture not found captureId=$captureId")
            }

        return try {
            captureRepository.updateProcessingStatus(captureId, ProcessingStatus.EXTRACTING_INTENT)

            val intentResult = aiOrchestrator.extractIntent(capture)

            // Convert validated IntentResult to domain Intent
            val intent = Intent(
                id = generateId(),
                captureId = captureId,
                type = runCatching { IntentType.valueOf(intentResult.type) }
                    .getOrDefault(IntentType.UNKNOWN),
                title = intentResult.title,
                description = intentResult.description,
                project = intentResult.project,
                deadline = intentResult.deadline,
                confidence = intentResult.confidence,
                status = IntentStatus.NEW,
                createdAt = nowMillis(),
            )

            intentRepository.save(intent)

            NudgeLogger.stageEnd("IntentWorker", captureId, 0)
            Result.success(inputData)
        } catch (e: Exception) {
            NudgeLogger.stageFailed("IntentWorker", captureId, e)
            if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure()
        }
    }

    companion object {
        private const val TAG = "IntentWorker"
        private const val MAX_RETRIES = 3
    }
}
