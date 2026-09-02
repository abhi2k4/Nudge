package com.nudge.ai.api

/**
 * Abstraction for text embedding generation.
 *
 * Embeddings power semantic search and relationship detection
 * in the context engine.
 *
 * Initial implementation is a stub. Future implementations may
 * use a quantized sentence-transformer model on-device.
 */
interface EmbeddingProvider {

    /**
     * Generates a fixed-size embedding vector for [text].
     * The vector dimension depends on the underlying model.
     */
    suspend fun embed(text: String): FloatArray

    /** Whether this embedding provider is ready on this device. */
    fun isAvailable(): Boolean
}
