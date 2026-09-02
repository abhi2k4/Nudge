package com.nudge.ai.runtime.di

import com.nudge.ai.runtime.CpuLanguageModel
import com.nudge.ai.runtime.MockLanguageModel
import com.nudge.ai.runtime.RuntimeSelector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AIRuntimeModule {

    @Provides
    @Singleton
    fun provideCpuLanguageModel(): CpuLanguageModel = CpuLanguageModel()

    @Provides
    @Singleton
    fun provideMockLanguageModel(): MockLanguageModel = MockLanguageModel()

    @Provides
    @Singleton
    fun provideRuntimeSelector(
        cpuModel: CpuLanguageModel,
        mockModel: MockLanguageModel,
    ): RuntimeSelector = RuntimeSelector(cpuModel, mockModel)
}
