package com.nudge.domain.usecase

import com.nudge.core.model.Intent
import com.nudge.core.model.IntentStatus
import com.nudge.domain.repository.IntentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** Observes intents filtered by status. Defaults to NEW (unreviewed). */
class GetIntentsUseCase @Inject constructor(
    private val intentRepository: IntentRepository,
) {
    operator fun invoke(status: IntentStatus = IntentStatus.NEW): Flow<List<Intent>> =
        intentRepository.observeByStatus(status)

    fun all(): Flow<List<Intent>> = intentRepository.observeAll()
}
