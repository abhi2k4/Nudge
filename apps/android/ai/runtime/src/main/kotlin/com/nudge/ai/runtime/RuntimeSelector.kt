package com.nudge.ai.runtime

import com.nudge.ai.api.OnDeviceLanguageModel
import com.nudge.ai.model.RuntimeCapabilities
import com.nudge.core.logging.NudgeLogger
import javax.inject.Inject

/**
 * Selects the best available [OnDeviceLanguageModel] based on [RuntimeCapabilities].
 *
 * Selection priority:
 *  1. NPU backend (fastest, lowest power) — only if verified available
 *  2. GPU backend — if available and model fits in GPU memory
 *  3. CPU backend (llama.cpp) — universal fallback
 *  4. Mock backend — development/test only
 *
 * This class must be updated whenever a new backend is added.
 */
class RuntimeSelector @Inject constructor(
    private val cpuModel: CpuLanguageModel,
    private val mockModel: MockLanguageModel,
) {
    fun select(capabilities: RuntimeCapabilities): OnDeviceLanguageModel {
        NudgeLogger.info(TAG, "Selecting AI runtime. capabilities=$capabilities")

        // NPU and GPU backends are not yet implemented.
        // When they are, add them here before the CPU check.

        if (cpuModel.isAvailable()) {
            NudgeLogger.info(TAG, "Selected: ${cpuModel.runtimeName}")
            return cpuModel
        }

        // Fall through to mock for development / CI builds.
        NudgeLogger.warn(TAG, "No production runtime available — falling back to mock model")
        return mockModel
    }

    private companion object {
        const val TAG = "RuntimeSelector"
    }
}
