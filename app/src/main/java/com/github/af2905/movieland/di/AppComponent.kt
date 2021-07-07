package com.github.af2905.movieland.di

import android.content.Context
import com.github.af2905.movieland.App
import com.github.af2905.movieland.di.module.AppModule
import com.github.af2905.movieland.di.module.NetworkModule
import com.github.af2905.movieland.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}