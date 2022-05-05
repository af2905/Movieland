package com.github.af2905.movieland.presentation.feature.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    private val _forceUpdate =
        MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    private val forceUpdate = _forceUpdate.asSharedFlow()

    private var job: Job? = null

    override fun subscribeOnForceUpdate(
        scope: CoroutineScope,
        collector: suspend (force: Boolean) -> Unit
    ) {
        //job = forceUpdate.launchCollect(scope, collector)
        job = scope.launch {
            forceUpdate.collect { collector(it) }
        }
    }

    //fun <T> Flow<T>.launchCollect(scope: CoroutineScope, collector: suspend (T) -> Unit) =
    //    scope.launch { collect(collector) }

    override fun forceUpdate() {
        _forceUpdate.tryEmit(true)
    }
}

interface HomeRepository {
    fun forceUpdate()
    fun subscribeOnForceUpdate(
        scope: CoroutineScope,
        collector: suspend (force: Boolean) -> Unit
    )
}