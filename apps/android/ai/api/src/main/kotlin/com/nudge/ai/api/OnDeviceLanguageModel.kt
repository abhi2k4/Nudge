package com.nudge.ai.api

import com.nudge.ai.model.ModelRequest
import com.nudge.ai.model.ModelResponse

/**
 * The primary contract for on-device language model inference.
 *
 * Implementations may use:
 *  - llama.cpp CPU backend
 *  - Qualcomm QNN / Hexagon NPU backend
 *  - MediaTek NeuroPilot
 *  - Any other accelerator
 *
 * The product layer must never depend on a concrete implementation.
 * Runtime selection happens in [com.nudge.ai.runtime.LocalAIManager].
 */
interface OnDeviceLanguageModel {

    /**
     * Runs inference and returns a structured response.
     *
     * @throws [com.nudge.ai.api.ModelInferenceException] on unrecoverable errors.
     */
    suspend fun generate(request: ModelRequest): ModelResponse

    /**
     * Returns whether this implementation is available on the current device.
     * Used by [com.nudge.ai.runtime.RuntimeSelector] to pick the best backend.
     */
    fun isAvailable(): Boolean

    /** Human-readable name for this backend (e.g. "llama.cpp CPU"). */
    val runtimeName: String
}

class ModelInferenceException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)
