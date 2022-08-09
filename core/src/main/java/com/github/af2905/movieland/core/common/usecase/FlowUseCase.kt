package com.github.af2905.movieland.core.common.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: P) = execute(params)
        .catch { e -> emit(Result.failure(Exception(e))) }
        .flowOn(coroutineDispatcher)

    protected abstract suspend fun execute(params: P): Flow<Result<R>>
}