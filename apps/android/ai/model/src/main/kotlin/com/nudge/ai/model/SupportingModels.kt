package com.nudge.ai.model

import kotlinx.serialization.Serializable

/** Result of transcribing audio to text. */
@Serializable
data class Transcript(
    val text: String,
    val confidence: Float,
    val languageCode: String = "en",
    val durationMs: Long? = null,
)

/** Result of the context resolution pass. */
@Serializable
data class ContextResolution(
    val intentId: String,
    val relatedCaptureIds: List<String> = emptyList(),
    val relatedContextIds: List<String> = emptyList(),
    val suggestedContextTitle: String? = null,
    val confidence: Float,
)

/**
 * Describes the AI capabilities of the current device.
 * Used by [com.nudge.ai.runtime.RuntimeSelector] to pick the best backend.
 *
 * NOTE: [supportsNpu] is only set to true when NPU execution has been
 * verified at runtime. Do not assume it based on device model alone.
 */
@Serializable
data class RuntimeCapabilities(
    val supportsCpu: Boolean = true,
    val supportsGpu: Boolean = false,
    val supportsNpu: Boolean = false,   // Only true when verified at runtime
    val maxContextLength: Int = 2048,
    val availableMemoryMb: Long = 0,
    val runtimeName: String,
)
