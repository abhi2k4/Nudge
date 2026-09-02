package com.nudge.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * Application entry point.
 *
 * Responsibilities:
 *  - Initialises Hilt dependency injection
 *  - Configures Timber logging (debug builds only)
 *  - Wires Hilt into WorkManager via [Configuration.Provider]
 */
@HiltAndroidApp
class NudgeApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // TODO: Plant a production crash-reporting tree (e.g. Firebase Crashlytics) for release builds
    }
}
