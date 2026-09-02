package com.nudge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nudge.core.database.dao.ActionDao
import com.nudge.core.database.dao.CaptureDao
import com.nudge.core.database.dao.IntentDao
import com.nudge.core.database.dao.NudgeContextDao
import com.nudge.core.database.dao.NudgeDao
import com.nudge.core.database.dao.RelationshipDao
import com.nudge.core.database.entity.ActionEntity
import com.nudge.core.database.entity.CaptureEntity
import com.nudge.core.database.entity.IntentEntity
import com.nudge.core.database.entity.NudgeContextEntity
import com.nudge.core.database.entity.NudgeEntity
import com.nudge.core.database.entity.RelationshipEntity

// ─────────────────────────────────────────────────────────────────────────────
// NudgeDatabase — the single Room database for the app.
//
// Migration strategy:
//   - During hackathon phase: fallbackToDestructiveMigration() is acceptable.
//   - Production: replace with explicit Migration objects before shipping.
//
// Schema export: exported to assets for documentation and future migration
//   verification. See autoMigrations path in build.gradle.kts if added.
// ─────────────────────────────────────────────────────────────────────────────

@Database(
    entities = [
        CaptureEntity::class,
        IntentEntity::class,
        ActionEntity::class,
        NudgeContextEntity::class,
        RelationshipEntity::class,
        NudgeEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class NudgeDatabase : RoomDatabase() {
    abstract fun captureDao(): CaptureDao
    abstract fun intentDao(): IntentDao
    abstract fun actionDao(): ActionDao
    abstract fun nudgeContextDao(): NudgeContextDao
    abstract fun relationshipDao(): RelationshipDao
    abstract fun nudgeDao(): NudgeDao

    companion object {
        const val DATABASE_NAME = "nudge.db"
    }
}
