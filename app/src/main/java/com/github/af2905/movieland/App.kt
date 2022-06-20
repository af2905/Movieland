package com.github.af2905.movieland

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.github.af2905.movieland.di.AppWorkerFactory
import com.github.af2905.movieland.di.CoreComponent
import com.github.af2905.movieland.di.DaggerCoreComponent
import timber.log.Timber

class App : Application(), CoreComponentStore {

    internal val coreComponent: CoreComponent = DaggerCoreComponent.factory().create(this)

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

object CoreComponentProvider {
    fun getAppComponent(context: Context): CoreComponent {
        return (context.applicationContext as CoreComponentStore).getComponent()
    }
}

interface CoreComponentStore {
    fun getComponent(): CoreComponent
}