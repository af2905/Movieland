package com.github.af2905.movieland.core.di.module

import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import com.github.af2905.movieland.core.data.worker.UpdateMoviesWorker
import com.github.af2905.movieland.core.di.AppWorkerFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass
//TODO Should be implemented?
/*
@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
@InstallIn(SingletonComponent::class)
interface WorkManagerModule {

    @Binds
    @IntoMap
    @WorkerKey(UpdateMoviesWorker::class)
    fun bindUpdateMoviesWorker(factory: UpdateMoviesWorker.Factory): WorkerFactory

    fun workerFactory(): AppWorkerFactory
}*/
