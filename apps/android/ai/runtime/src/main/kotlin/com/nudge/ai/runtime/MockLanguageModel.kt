package com.nudge.ai.runtime

import com.nudge.ai.api.ModelInferenceException
import com.nudge.ai.api.OnDeviceLanguageModel
import com.nudge.ai.model.ModelRequest
import com.nudge.ai.model.ModelResponse
import com.nudge.core.logging.NudgeLogger

/**
 * In-memory mock language model for development and testing.
 *
 * Returns a canned JSON response that resembles a valid IntentResult.
 * This allows the full pipeline to be exercised without a real model.
 *
 * DO NOT use in production builds.
 */
class MockLanguageModel : OnDeviceLanguageModel {

    override val runtimeName: String = "Mock (Development Only)"

    override fun isAvailable(): Boolean = true

    override suspend fun generate(request: ModelRequest): ModelResponse {
        NudgeLogger.debug(TAG, "MockLanguageModel.generate() called — returning canned response")

        val mockJson = """
            {
              "type": "REMINDER",
              "title": "Follow up on this",
              "description": "Extracted from your capture",
              "project": null,
              "deadline": null,
              "confidence": 0.75
            }
        """.trimIndent()

        return ModelResponse(
            text = mockJson,
            tokensGenerated = mockJson.split(" ").size,
            finishReason = "stop",
            latencyMs = 50L,
        )
    }

    private companion object {
        const val TAG = "MockLanguageModel"
    }
}
