package com.nudge.ai.intent

import com.nudge.ai.api.IntentExtractor
import com.nudge.ai.model.IntentResult
import com.nudge.ai.model.IntentValidationResult
import com.nudge.ai.model.ModelRequest
import com.nudge.ai.runtime.LocalAIManager
import com.nudge.core.logging.NudgeLogger
import com.nudge.core.model.Capture
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Extracts structured intent from a [Capture] by calling the on-device LLM
 * with a structured prompt and parsing the JSON response.
 *
 * Safety guarantees:
 *  - LLM output is always parsed and validated before use.
 *  - Invalid JSON or missing required fields result in [IntentValidationResult.Invalid].
 *  - Raw LLM output is preserved for debugging but never stored as-is.
 */
class IntentExtractorImpl @Inject constructor(
    private val aiManager: LocalAIManager,
) : IntentExtractor {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun extract(capture: Capture): IntentResult {
        NudgeLogger.stageStart("IntentExtraction", capture.id)
        val start = System.currentTimeMillis()

        val captureContent = buildCaptureContent(capture)
        val request = ModelRequest(
            systemPrompt = SYSTEM_PROMPT,
            userPrompt = captureContent,
            maxTokens = 256,
            temperature = 0.1f,
            stopSequences = listOf("}"),
        )

        val response = aiManager.generate(request)
        val rawOutput = response.text + "}"  // Re-append stop sequence

        return when (val validation = validate(rawOutput)) {
            is IntentValidationResult.Valid -> {
                NudgeLogger.stageEnd("IntentExtraction", capture.id, System.currentTimeMillis() - start)
                validation.result
            }
            is IntentValidationResult.Invalid -> {
                NudgeLogger.warn(
                    TAG,
                    "Intent validation failed for captureId=${capture.id}: ${validation.reason}"
                )
                NudgeLogger.stageEnd("IntentExtraction", capture.id, System.currentTimeMillis() - start)
                // Return a low-confidence fallback rather than propagating failure
                IntentResult(
                    type = "UNKNOWN",
                    title = "Captured item",
                    confidence = 0.0f,
                    rawOutput = rawOutput,
                )
            }
        }
    }

    private fun validate(rawOutput: String): IntentValidationResult {
        return try {
            val result = json.decodeFromString<IntentResult>(rawOutput)
            if (result.title.isBlank()) {
                IntentValidationResult.Invalid("title is blank", rawOutput)
            } else if (result.confidence < 0f || result.confidence > 1f) {
                IntentValidationResult.Invalid("confidence out of range: ${result.confidence}", rawOutput)
            } else {
                IntentValidationResult.Valid(result.copy(rawOutput = rawOutput))
            }
        } catch (e: Exception) {
            IntentValidationResult.Invalid("JSON parse error: ${e.message}", rawOutput)
        }
    }

    private fun buildCaptureContent(capture: Capture): String = buildString {
        appendLine("Capture type: ${capture.type.name}")
        capture.text?.let { appendLine("Content: $it") }
        if (capture.metadata.isNotEmpty()) {
            appendLine("Metadata: ${capture.metadata}")
        }
    }

    private companion object {
        const val TAG = "IntentExtractorImpl"
        val SYSTEM_PROMPT = """
            You are an intent extraction assistant. Given a user's captured content, 
            extract the user's intent and respond ONLY with a valid JSON object matching this schema:
            {
              "type": "<REMINDER|TASK|NOTE|RESEARCH|BOOKING|PURCHASE|MEETING|UNKNOWN>",
              "title": "<short action title>",
              "description": "<optional longer description or null>",
              "project": "<optional project name or null>",
              "deadline": "<optional human-readable deadline or null>",
              "confidence": <float 0.0-1.0>
            }
            Do not include any text before or after the JSON object.
        """.trimIndent()
    }
}
