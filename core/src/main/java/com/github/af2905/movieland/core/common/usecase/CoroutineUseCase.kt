package com.github.af2905.movieland.core.common.usecase

import timber.log.Timber

abstract class CoroutineUseCase<in P, R> {

    suspend operator fun invoke(params: P): Result<R> {
        return try {
            Result.success(execute(params))
        } catch (e: Exception) {
            Timber.d(e)
            Result.failure(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: P): R

}