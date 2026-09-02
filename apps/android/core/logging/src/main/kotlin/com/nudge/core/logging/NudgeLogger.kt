package com.nudge.core.logging

import timber.log.Timber

// ─────────────────────────────────────────────────────────────────────────────
// NudgeLogger — a thin, structured logging façade over Timber.
// Use this instead of calling Timber directly, so we can add structured
// log enrichment (tags, processing stage, correlationId) in one place.
// ─────────────────────────────────────────────────────────────────────────────

object NudgeLogger {

    fun debug(tag: String, message: String, vararg args: Any?) {
        Timber.tag(tag).d(message, *args)
    }

    fun info(tag: String, message: String, vararg args: Any?) {
        Timber.tag(tag).i(message, *args)
    }

    fun warn(tag: String, message: String, vararg args: Any?) {
        Timber.tag(tag).w(message, *args)
    }

    fun error(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Timber.tag(tag).e(throwable, message)
        } else {
            Timber.tag(tag).e(message)
        }
    }

    /**
     * Logs the start of a named processing stage.
     * Paired with [stageEnd] to measure pipeline durations.
     */
    fun stageStart(stage: String, captureId: String) {
        Timber.tag("NudgePipeline").i("▶ START stage=$stage captureId=$captureId")
    }

    /**
     * Logs the successful completion of a named processing stage.
     */
    fun stageEnd(stage: String, captureId: String, durationMs: Long) {
        Timber.tag("NudgePipeline").i("✔ END stage=$stage captureId=$captureId durationMs=$durationMs")
    }

    /**
     * Logs a failure in a named processing stage. Never swallows the error.
     */
    fun stageFailed(stage: String, captureId: String, cause: Throwable) {
        Timber.tag("NudgePipeline").e(cause, "✘ FAILED stage=$stage captureId=$captureId")
    }
}
