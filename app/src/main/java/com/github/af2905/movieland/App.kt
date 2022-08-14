package com.github.af2905.movieland

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.github.af2905.movieland.core.di.AppWorkerFactory
import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.CoreComponentStore
import com.github.af2905.movieland.core.di.DaggerCoreComponent
import timber.log.Timber

class App : Application(), CoreComponentStore {

    private val coreComponent: CoreComponent = DaggerCoreComponent.factory().create(this)

    private val workerFactory: AppWorkerFactory = coreComponent.getAppWorkerFactory()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }

    override fun getComponent(): CoreComponent {
        return coreComponent
    }
}
