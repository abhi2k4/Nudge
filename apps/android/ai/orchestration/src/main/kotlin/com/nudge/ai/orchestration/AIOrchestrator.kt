package com.nudge.ai.orchestration

import com.nudge.ai.api.IntentExtractor
import com.nudge.ai.model.IntentResult
import com.nudge.core.logging.NudgeLogger
import com.nudge.core.model.Capture
import javax.inject.Inject

/**
 * Represents the local (on-device) AI processing path.
 *
 * Routes through [IntentExtractor] which uses [LocalAIManager] internally.
 */
class LocalAIProvider @Inject constructor(
    private val intentExtractor: IntentExtractor,
) {
    suspend fun extractIntent(capture: Capture): IntentResult {
        NudgeLogger.debug(TAG, "LocalAIProvider.extractIntent() for captureId=${capture.id}")
        return intentExtractor.extract(capture)
    }

    private companion object {
        const val TAG = "LocalAIProvider"
    }
}

/**
 * Represents the cloud AI escalation path.
 *
 * STATUS: NOT IMPLEMENTED — interface only.
 *
 * Privacy principle: No captured data is sent to the cloud without
 * an explicit user opt-in. This class exists to define the boundary
 * but must never be called automatically.
 */
class CloudAIProvider @Inject constructor() {

    fun isConfigured(): Boolean = false

    suspend fun extractIntent(capture: Capture): IntentResult {
        // TODO: Implement cloud AI call after:
        //  1. Explicit user consent for cloud processing is obtained
        //  2. Supabase Edge Functions / external AI API configured
        //  3. Data minimisation review completed
        throw UnsupportedOperationException(
            "Cloud AI provider is not yet implemented. " +
                    "Explicit user consent must be obtained before implementing this."
        )
    }
}

/**
 * Orchestrates the decision between local and cloud AI processing.
 *
 * Decision logic:
 *  1. Always try local processing first.
 *  2. If local processing fails and cloud is configured AND user has opted in,
 *     escalate to cloud.
 *  3. If cloud is unavailable, return the local failure without retrying.
 *
 * Data sovereignty: Cloud escalation must be gated by explicit user preference.
 */
class AIOrchestrator @Inject constructor(
    private val localProvider: LocalAIProvider,
    private val cloudProvider: CloudAIProvider,
) {
    /** Whether cloud processing is allowed for this session. */
    private var cloudConsentGranted: Boolean = false

    /**
     * Call after user explicitly enables cloud processing in settings.
     */
    fun grantCloudConsent() {
        NudgeLogger.info(TAG, "User granted cloud AI processing consent")
        cloudConsentGranted = true
    }

    fun revokeCloudConsent() {
        NudgeLogger.info(TAG, "User revoked cloud AI processing consent")
        cloudConsentGranted = false
    }

    suspend fun extractIntent(capture: Capture): IntentResult {
        return try {
            localProvider.extractIntent(capture)
        } catch (localError: Exception) {
            NudgeLogger.error(TAG, "Local AI failed for captureId=${capture.id}", localError)

            if (cloudConsentGranted && cloudProvider.isConfigured()) {
                NudgeLogger.info(TAG, "Escalating to cloud AI for captureId=${capture.id}")
                cloudProvider.extractIntent(capture)
            } else {
                NudgeLogger.warn(TAG, "Cloud AI not available/consented — returning failure")
                throw localError
            }
        }
    }

    private companion object {
        const val TAG = "AIOrchestrator"
    }
}
