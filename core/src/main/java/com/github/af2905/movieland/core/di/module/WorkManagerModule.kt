package com.github.af2905.movieland.core.di.module

import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import com.github.af2905.movieland.core.data.worker.UpdateMoviesWorker
import com.github.af2905.movieland.core.di.AppWorkerFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
interface WorkManagerModule {

    @Binds
    @IntoMap
    @WorkerKey(UpdateMoviesWorker::class)
    fun bindUpdateMoviesWorker(factory: UpdateMoviesWorker.Factory): WorkerFactory

    fun workerFactory(): AppWorkerFactory
}