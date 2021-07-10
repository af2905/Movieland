package com.github.af2905.movieland.domain.usecase

import com.github.af2905.movieland.data.error.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.invoke
import timber.log.Timber

abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            coroutineDispatcher {
                execute(parameters).let { Result.Success(it) }
            }
        } catch (e: Exception) {
            Timber.d(e)
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R

}