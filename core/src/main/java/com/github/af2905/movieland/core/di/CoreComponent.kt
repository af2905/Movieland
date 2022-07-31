package com.github.af2905.movieland.core.di

import android.content.Context
import com.github.af2905.movieland.core.di.module.*
import com.github.af2905.movieland.core.di.scope.AppScope
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.core.repository.SearchRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        StorageModule::class,
        WorkManagerModule::class,
        ResourceModule::class
    ]
)
interface CoreComponent {

    fun getMoviesRepository(): MoviesRepository
    fun getPeopleRepository(): PeopleRepository
    fun getSearchRepository(): SearchRepository
    fun getAppWorkerFactory(): AppWorkerFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }
}