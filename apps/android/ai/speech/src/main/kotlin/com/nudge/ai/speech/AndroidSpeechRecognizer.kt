package com.nudge.ai.speech

import android.net.Uri
import com.nudge.ai.api.SpeechRecognizer
import com.nudge.ai.model.Transcript
import com.nudge.core.logging.NudgeLogger
import javax.inject.Inject

/**
 * ASR implementation wrapping the Android platform SpeechRecognizer API.
 *
 * STATUS: STUB — actual SpeechRecognizer integration pending.
 *
 * Notes:
 *  - Android's built-in SpeechRecognizer requires an active internet connection
 *    unless using the on-device recognizer (Android 13+).
 *  - For fully offline ASR, replace this with a Whisper.cpp JNI bridge
 *    or the Android 13+ on-device SpeechRecognizer.
 *  - The interface is designed to swap implementations without touching callers.
 */
class AndroidSpeechRecognizer @Inject constructor() : SpeechRecognizer {

    override fun isAvailable(): Boolean {
        // TODO: Check android.speech.SpeechRecognizer.isRecognitionAvailable()
        return false
    }

    override suspend fun transcribe(audioUri: Uri): Transcript {
        NudgeLogger.warn(TAG, "AndroidSpeechRecognizer is a stub — transcription not implemented")
        return Transcript(
            text = "",
            confidence = 0f,
            languageCode = "en",
        )
    }

    private companion object {
        const val TAG = "AndroidSpeechRecognizer"
    }
}
