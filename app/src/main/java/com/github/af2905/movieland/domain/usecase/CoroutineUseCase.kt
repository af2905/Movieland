package com.github.af2905.movieland.domain.usecase

import com.github.af2905.movieland.data.error.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

abstract class CoroutineUseCase<in P, R> {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(params: P): Result<R> {
        return try {
            execute(params).let { Result.Success(it) }
        } catch (e: Exception) {
            Timber.d(e)
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: P): R

}