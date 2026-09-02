package com.nudge.sync.conflict

/**
 * Resolves conflicts when the same entity has been modified both locally
 * and remotely since the last sync.
 *
 * Resolution strategies:
 *  - [LastWriteWins]: whichever version has the later timestamp wins.
 *  - [LocalWins]: local version always takes precedence.
 *  - [RemoteWins]: remote version always takes precedence.
 *
 * A more sophisticated user-facing conflict resolution UI is a next-phase task.
 */
interface ConflictResolver {
    fun <T : Conflictable> resolve(local: T, remote: T): ConflictResolution<T>
}

interface Conflictable {
    val id: String
    val updatedAt: Long
}

sealed class ConflictResolution<T> {
    data class UseLocal<T>(val entity: T) : ConflictResolution<T>()
    data class UseRemote<T>(val entity: T) : ConflictResolution<T>()
    data class Merged<T>(val entity: T) : ConflictResolution<T>()
}

/** Simple last-write-wins strategy based on timestamp comparison. */
class LastWriteWinsResolver : ConflictResolver {
    override fun <T : Conflictable> resolve(local: T, remote: T): ConflictResolution<T> =
        if (local.updatedAt >= remote.updatedAt) {
            ConflictResolution.UseLocal(local)
        } else {
            ConflictResolution.UseRemote(remote)
        }
}
