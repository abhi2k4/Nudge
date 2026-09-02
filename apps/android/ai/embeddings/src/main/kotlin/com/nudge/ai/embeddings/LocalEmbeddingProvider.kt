package com.nudge.ai.embeddings

import com.nudge.ai.api.EmbeddingProvider
import com.nudge.core.logging.NudgeLogger
import javax.inject.Inject

/**
 * Stub embedding provider for the initial foundation phase.
 *
 * STATUS: STUB — not yet implemented.
 *
 * Integration plan:
 *  - Use a quantized sentence-transformer (e.g. all-MiniLM-L6 via ONNX Runtime)
 *    for 384-dimensional embeddings on-device.
 *  - Alternatively, use the same llama.cpp backend to generate embeddings.
 *
 * Until this is implemented, all returned embeddings are zero vectors.
 * This means semantic search will not work, but the pipeline will not fail.
 */
class LocalEmbeddingProvider @Inject constructor() : EmbeddingProvider {

    override fun isAvailable(): Boolean = false

    override suspend fun embed(text: String): FloatArray {
        NudgeLogger.warn(TAG, "LocalEmbeddingProvider is a stub — returning zero vector")
        // Return a zero vector of standard 384-dim size as a placeholder
        return FloatArray(EMBEDDING_DIM) { 0f }
    }

    private companion object {
        const val TAG = "LocalEmbeddingProvider"
        const val EMBEDDING_DIM = 384
    }
}
