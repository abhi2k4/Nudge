package com.nudge.ai.intent.di

import com.nudge.ai.api.IntentExtractor
import com.nudge.ai.intent.IntentExtractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class IntentModule {
    @Binds
    @Singleton
    abstract fun bindIntentExtractor(
        impl: IntentExtractorImpl,
    ): IntentExtractor
}
