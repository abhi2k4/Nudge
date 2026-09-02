package com.nudge.core.database.di

import android.content.Context
import androidx.room.Room
import com.nudge.core.database.NudgeDatabase
import com.nudge.core.database.dao.ActionDao
import com.nudge.core.database.dao.CaptureDao
import com.nudge.core.database.dao.IntentDao
import com.nudge.core.database.dao.NudgeContextDao
import com.nudge.core.database.dao.NudgeDao
import com.nudge.core.database.dao.RelationshipDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNudgeDatabase(@ApplicationContext context: Context): NudgeDatabase =
        Room.databaseBuilder(
            context,
            NudgeDatabase::class.java,
            NudgeDatabase.DATABASE_NAME,
        )
            // TODO: Replace with explicit Migration objects before production release.
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideCaptureDao(db: NudgeDatabase): CaptureDao = db.captureDao()
    @Provides fun provideIntentDao(db: NudgeDatabase): IntentDao = db.intentDao()
    @Provides fun provideActionDao(db: NudgeDatabase): ActionDao = db.actionDao()
    @Provides fun provideNudgeContextDao(db: NudgeDatabase): NudgeContextDao = db.nudgeContextDao()
    @Provides fun provideRelationshipDao(db: NudgeDatabase): RelationshipDao = db.relationshipDao()
    @Provides fun provideNudgeDao(db: NudgeDatabase): NudgeDao = db.nudgeDao()
}
