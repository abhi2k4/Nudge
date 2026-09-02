package com.nudge.domain.usecase

import com.nudge.core.model.NudgeContext
import com.nudge.domain.repository.ContextRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContextUseCase @Inject constructor(
    private val contextRepository: ContextRepository,
) {
    operator fun invoke(): Flow<List<NudgeContext>> = contextRepository.observeAll()

    suspend fun recent(limit: Int = 20): List<NudgeContext> =
        contextRepository.getRecent(limit)
}
