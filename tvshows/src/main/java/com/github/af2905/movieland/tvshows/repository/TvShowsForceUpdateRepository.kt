package com.github.af2905.movieland.tvshows.repository

import com.github.af2905.movieland.core.common.helper.launchCollect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class TvShowsForceUpdateRepositoryImpl @Inject constructor() : TvShowsForceUpdateRepository {

    private val _forceUpdate =
        MutableSharedFlow<Boolean>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    private val forceUpdate = _forceUpdate.asSharedFlow()

    private var job: Job? = null

    override fun subscribeOnForceUpdate(scope: CoroutineScope, collector: FlowCollector<Boolean>) {
        job = forceUpdate.launchCollect(scope, collector)
    }

    override fun forceUpdate() {
        _forceUpdate.tryEmit(true)
    }
}

interface TvShowsForceUpdateRepository {
    fun forceUpdate()
    fun subscribeOnForceUpdate(scope: CoroutineScope, collector: FlowCollector<Boolean>)
}