package com.nudge.domain.usecase

import com.nudge.core.common.NudgeResult
import com.nudge.core.model.Capture
import com.nudge.domain.repository.CaptureRepository
import javax.inject.Inject

/**
 * Saves a new capture to local storage.
 *
 * This is the entry point for all user captures. It must succeed
 * regardless of AI or network availability. The capture is enqueued
 * for async AI processing after successful persistence.
 *
 * Callers must supply a fully-formed [Capture] with a generated ID.
 */
class SaveCaptureUseCase @Inject constructor(
    private val captureRepository: CaptureRepository,
) {
    suspend operator fun invoke(capture: Capture): NudgeResult<Capture> {
        require(capture.id.isNotBlank()) { "Capture ID must not be blank" }
        return captureRepository.save(capture)
    }
}
