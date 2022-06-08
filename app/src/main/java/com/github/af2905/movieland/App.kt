package com.github.af2905.movieland

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.AppWorkerFactory
import com.github.af2905.movieland.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    internal val appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    val workerFactory: AppWorkerFactory = appComponent.getAppWorkerFactory()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }
}

object AppComponentProvider {
    fun getAppComponent(context: Context): AppComponent {
        return (context.applicationContext as App).appComponent
    }
}