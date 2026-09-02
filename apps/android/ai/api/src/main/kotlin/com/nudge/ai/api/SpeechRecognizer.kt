package com.nudge.ai.api

import android.net.Uri
import com.nudge.ai.model.Transcript

/**
 * Abstraction for on-device Automatic Speech Recognition (ASR).
 *
 * Initial implementation wraps Android's built-in SpeechRecognizer.
 * Future implementations may use Whisper.cpp or a dedicated NPU model.
 */
interface SpeechRecognizer {

    /**
     * Transcribes audio from [audioUri] and returns a structured [Transcript].
     *
     * The URI must point to a local audio file accessible by the app.
     */
    suspend fun transcribe(audioUri: Uri): Transcript

    /** Whether this ASR implementation is ready for use on this device. */
    fun isAvailable(): Boolean
}
