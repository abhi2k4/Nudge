package com.nudge.ai.model

import kotlinx.serialization.Serializable

/**
 * Structured output from intent extraction.
 *
 * This is the validated result of parsing LLM output.
 * Raw LLM text must never be stored directly — only validated [IntentResult]
 * instances are converted to domain [com.nudge.core.model.Intent] objects.
 *
 * Corresponds to the JSON schema:
 * ```json
 * {
 *   "type": "REMINDER",
 *   "title": "Book train ticket",
 *   "deadline": "this weekend",
 *   "project": "Goa Trip",
 *   "confidence": 0.94
 * }
 * ```
 */
@Serializable
data class IntentResult(
    val type: String,               // Maps to IntentType enum after validation
    val title: String,
    val description: String? = null,
    val project: String? = null,
    val deadline: String? = null,
    val confidence: Float,
    val rawOutput: String? = null,  // Preserved for debugging; never shown to user
)

/** Validation outcome before converting [IntentResult] to a domain intent. */
sealed class IntentValidationResult {
    data class Valid(val result: IntentResult) : IntentValidationResult()
    data class Invalid(val reason: String, val rawOutput: String) : IntentValidationResult()
}
