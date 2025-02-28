package com.github.af2905.movieland.core.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.af2905.movieland.core.repository.MoviesRepository
import timber.log.Timber
import javax.inject.Inject

@HiltWorker
class UpdateMoviesWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: MoviesRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val result = Result.success()
        Timber.d("worker name: $WORKER_NAME, result: $result")
        return result
    }

    class Factory @Inject constructor(
        private val repository: MoviesRepository
    ) : WorkerFactory() {

        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return UpdateMoviesWorker(appContext, workerParameters, repository)
        }
    }

    companion object {
        const val WORKER_NAME = "UpdateMoviesWorker"
    }
}
