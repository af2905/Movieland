package com.github.af2905.movieland

import androidx.work.Configuration
import androidx.work.WorkManager
import com.github.af2905.movieland.di.AppWorkerFactory
import com.github.af2905.movieland.di.DaggerAppComponent
import com.github.af2905.movieland.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var workerFactory: AppWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .context(this)
            .appModule(AppModule(applicationContext))
            .build()
    }
}