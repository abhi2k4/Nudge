package com.nudge.ai.runtime

import com.nudge.ai.api.ModelInferenceException
import com.nudge.ai.api.OnDeviceLanguageModel
import com.nudge.ai.model.ModelRequest
import com.nudge.ai.model.ModelResponse
import com.nudge.core.logging.NudgeLogger

/**
 * CPU-based language model backend using llama.cpp via JNI.
 *
 * STATUS: STUB — JNI bindings not yet implemented.
 *
 * Integration plan:
 *  1. Add llama.cpp as a git submodule under ai/runtime/src/main/cpp/
 *  2. Write JNI bridge (LlamaCppBridge.kt + native/llama_bridge.cpp)
 *  3. Configure CMakeLists.txt in this module
 *  4. Replace the UnsupportedOperationException below with actual inference
 *
 * This stub is intentionally non-functional so the rest of the pipeline
 * can be exercised via [MockLanguageModel].
 */
class CpuLanguageModel : OnDeviceLanguageModel {

    override val runtimeName: String = "llama.cpp CPU (stub — not yet implemented)"

    override fun isAvailable(): Boolean {
        // TODO: Check if model file exists at the expected path
        NudgeLogger.warn(TAG, "CpuLanguageModel.isAvailable() — JNI not yet implemented, returning false")
        return false
    }

    override suspend fun generate(request: ModelRequest): ModelResponse {
        // TODO: Call into llama.cpp JNI bridge
        throw ModelInferenceException(
            "llama.cpp CPU backend is not yet implemented. " +
                    "Use MockLanguageModel for development.",
        )
    }

    private companion object {
        const val TAG = "CpuLanguageModel"
    }
}
