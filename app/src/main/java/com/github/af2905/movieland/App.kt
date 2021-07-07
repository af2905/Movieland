package com.github.af2905.movieland

import android.app.Application
import com.github.af2905.movieland.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().context(this).build().inject(this)
        Timber.plant(Timber.DebugTree())
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}