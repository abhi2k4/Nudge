package com.nudge.data.repository.di

import com.nudge.data.repository.CaptureRepositoryImpl
import com.nudge.data.repository.IntentRepositoryImpl
import com.nudge.domain.repository.CaptureRepository
import com.nudge.domain.repository.ContextRepository
import com.nudge.domain.repository.IntentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCaptureRepository(
        impl: CaptureRepositoryImpl,
    ): CaptureRepository

    @Binds
    @Singleton
    abstract fun bindIntentRepository(
        impl: IntentRepositoryImpl,
    ): IntentRepository
}
