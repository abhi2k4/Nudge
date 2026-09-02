package com.nudge.ai.api

import com.nudge.ai.model.IntentResult
import com.nudge.core.model.Capture

/**
 * Extracts structured intent from a [Capture].
 *
 * The extractor must:
 *  - Call the on-device LLM with a structured prompt.
 *  - Parse and validate the JSON response.
 *  - Return a strongly-typed [IntentResult], never raw LLM text.
 *  - Never let LLM output directly mutate the database.
 */
interface IntentExtractor {

    suspend fun extract(capture: Capture): IntentResult
}
