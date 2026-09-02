package com.nudge.core.common

import java.util.UUID

/** Generates a new random UUID string. Used for entity ID creation. */
fun generateId(): String = UUID.randomUUID().toString()

/** Returns the current time as epoch milliseconds. */
fun nowMillis(): Long = System.currentTimeMillis()
