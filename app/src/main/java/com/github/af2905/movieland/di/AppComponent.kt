package com.github.af2905.movieland.di

import android.content.Context
import com.github.af2905.movieland.App
import com.github.af2905.movieland.di.module.*
import com.github.af2905.movieland.di.qualifier.AppContext
import com.github.af2905.movieland.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModule::class,
        StorageModule::class,
        WorkManagerModule::class,
        ResourceModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @AppContext
        @BindsInstance
        fun context(context: Context): Builder
        fun appModule(module: AppModule): Builder // убрать т.к. добавили @AppContext
        fun build(): AppComponent
    }
}