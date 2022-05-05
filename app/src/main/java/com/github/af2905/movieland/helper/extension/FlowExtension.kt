package com.github.af2905.movieland.helper.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchCollect(scope: CoroutineScope, collector: suspend (T) -> Unit) =
    scope.launch {
        //collect(collector)
    }