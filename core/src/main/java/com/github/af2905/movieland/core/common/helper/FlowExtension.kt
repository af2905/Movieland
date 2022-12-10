package com.github.af2905.movieland.core.common.helper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchCollect(scope: CoroutineScope, collector: FlowCollector<T>) =
    scope.launch { collect(collector) }