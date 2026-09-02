package com.nudge.ai.model

import kotlinx.serialization.Serializable

/**
 * Input to the on-device language model.
 *
 * @param systemPrompt Optional system/role prompt.
 * @param userPrompt The user-facing content to process.
 * @param maxTokens Maximum tokens to generate in the response.
 * @param temperature Sampling temperature (0.0 = deterministic, 1.0 = creative).
 * @param stopSequences Token sequences that signal end-of-output.
 */
@Serializable
data class ModelRequest(
    val systemPrompt: String? = null,
    val userPrompt: String,
    val maxTokens: Int = 512,
    val temperature: Float = 0.2f,
    val stopSequences: List<String> = emptyList(),
)

/**
 * Output from the on-device language model.
 *
 * @param text Generated text content.
 * @param tokensGenerated Number of tokens produced.
 * @param finishReason Why generation stopped ("stop", "length", "error").
 */
@Serializable
data class ModelResponse(
    val text: String,
    val tokensGenerated: Int,
    val finishReason: String,
    val latencyMs: Long,
)
