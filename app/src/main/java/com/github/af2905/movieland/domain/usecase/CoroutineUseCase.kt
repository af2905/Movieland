package com.github.af2905.movieland.domain.usecase

import com.github.af2905.movieland.data.result.Result
import timber.log.Timber

abstract class CoroutineUseCase<in P, R> {

    suspend operator fun invoke(params: P): Result<R> {
        return try {
            Result.Success(execute(params))
        } catch (e: Exception) {
            Timber.d(e)
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: P): R

}