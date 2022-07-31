package com.github.af2905.movieland.helper.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
fun <T> Flow<T>.launchCollect(scope: CoroutineScope, collector: FlowCollector<T>) =
    scope.launch {
        collect(collector)
    }