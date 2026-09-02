package com.nudge.sync.di

import com.nudge.sync.engine.LocalSyncEngine
import com.nudge.sync.engine.SyncEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {
    @Binds
    @Singleton
    abstract fun bindSyncEngine(
        impl: LocalSyncEngine,
    ): SyncEngine
}
