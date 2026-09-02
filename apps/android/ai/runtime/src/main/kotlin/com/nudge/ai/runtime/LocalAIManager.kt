package com.nudge.ai.runtime

import android.app.ActivityManager
import android.content.Context
import com.nudge.ai.api.OnDeviceLanguageModel
import com.nudge.ai.model.ModelRequest
import com.nudge.ai.model.ModelResponse
import com.nudge.ai.model.RuntimeCapabilities
import com.nudge.core.logging.NudgeLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central manager for on-device AI inference.
 *
 * [LocalAIManager] is the single point of entry for all AI calls in the app.
 * It:
 *  1. Probes device capabilities at startup.
 *  2. Uses [RuntimeSelector] to choose the best available backend.
 *  3. Routes all [generate] calls to the selected backend.
 *  4. Logs inference latency and errors.
 *
 * Never call [OnDeviceLanguageModel] implementations directly from
 * outside the `ai` module.
 */
@Singleton
class LocalAIManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val runtimeSelector: RuntimeSelector,
) {
    val capabilities: RuntimeCapabilities by lazy { probeCapabilities() }
    private val model: OnDeviceLanguageModel by lazy { runtimeSelector.select(capabilities) }

    suspend fun generate(request: ModelRequest): ModelResponse {
        val start = System.currentTimeMillis()
        NudgeLogger.debug(TAG, "generate() — using runtime: ${model.runtimeName}")
        return try {
            model.generate(request).also {
                NudgeLogger.debug(TAG, "generate() complete in ${System.currentTimeMillis() - start}ms")
            }
        } catch (e: Exception) {
            NudgeLogger.error(TAG, "generate() failed", e)
            throw e
        }
    }



    private fun probeCapabilities(): RuntimeCapabilities {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo().also { activityManager.getMemoryInfo(it) }
        val availableMb = memInfo.availMem / (1024 * 1024)

        return RuntimeCapabilities(
            supportsCpu = true,
            supportsGpu = false,    // TODO: probe via OpenCL/Vulkan availability
            supportsNpu = false,    // TODO: probe via QNN / NNAPI delegate check
            maxContextLength = 2048,
            availableMemoryMb = availableMb,
            runtimeName = "LocalAIManager",
        ).also {
            NudgeLogger.info(TAG, "Runtime capabilities: $it")
        }
    }

    private companion object {
        const val TAG = "LocalAIManager"
    }
}
