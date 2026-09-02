package com.nudge.ai.api

import com.nudge.ai.model.ContextResolution
import com.nudge.core.model.Intent

/**
 * Resolves related context for a given [Intent].
 *
 * Uses embedding similarity and relationship traversal to find
 * semantically related captures, contexts, and prior intents.
 */
interface ContextResolver {

    suspend fun resolve(intent: Intent): ContextResolution
}
