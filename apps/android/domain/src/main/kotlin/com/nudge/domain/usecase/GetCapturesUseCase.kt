package com.nudge.domain.usecase

import com.nudge.core.model.Capture
import com.nudge.domain.repository.CaptureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** Observes all captures as a reactive Flow, newest-first. */
class GetCapturesUseCase @Inject constructor(
    private val captureRepository: CaptureRepository,
) {
    operator fun invoke(): Flow<List<Capture>> = captureRepository.observeAll()
}
