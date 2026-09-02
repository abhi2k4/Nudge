package com.nudge.core.common

// ─────────────────────────────────────────────────────────────────────────────
// Result — a sealed wrapper for success/failure that carries error details
// without relying on exceptions flowing across layer boundaries.
// ─────────────────────────────────────────────────────────────────────────────

sealed class NudgeResult<out T> {

    data class Success<T>(val data: T) : NudgeResult<T>()

    data class Error(
        val message: String,
        val cause: Throwable? = null,
        val code: ErrorCode = ErrorCode.UNKNOWN,
    ) : NudgeResult<Nothing>()

    object Loading : NudgeResult<Nothing>()
}

enum class ErrorCode {
    UNKNOWN,
    IO_ERROR,
    NETWORK_ERROR,
    AI_PROCESSING_ERROR,
    VALIDATION_ERROR,
    NOT_FOUND,
    PERMISSION_DENIED,
    SYNC_CONFLICT,
}

// ── Extensions ────────────────────────────────────────────────────────────────

inline fun <T> NudgeResult<T>.onSuccess(action: (T) -> Unit): NudgeResult<T> {
    if (this is NudgeResult.Success) action(data)
    return this
}

inline fun <T> NudgeResult<T>.onError(action: (NudgeResult.Error) -> Unit): NudgeResult<T> {
    if (this is NudgeResult.Error) action(this)
    return this
}

val <T> NudgeResult<T>.dataOrNull: T?
    get() = (this as? NudgeResult.Success)?.data

val <T> NudgeResult<T>.isSuccess: Boolean
    get() = this is NudgeResult.Success

val <T> NudgeResult<T>.isLoading: Boolean
    get() = this is NudgeResult.Loading

/** Wraps a suspend block and catches all exceptions into [NudgeResult.Error]. */
suspend fun <T> safeCall(block: suspend () -> T): NudgeResult<T> = try {
    NudgeResult.Success(block())
} catch (e: Exception) {
    NudgeResult.Error(
        message = e.message ?: "Unknown error",
        cause = e,
    )
}
