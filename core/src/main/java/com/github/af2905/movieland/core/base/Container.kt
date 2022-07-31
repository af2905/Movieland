@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.github.af2905.movieland.core.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

class Container<STATE, EFFECT>(private val scope: CoroutineScope, initialState: STATE) {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect: Flow<EFFECT> = _effect.receiveAsFlow()

    fun intent(transform: suspend Container<STATE, EFFECT>.() -> Unit) {
        scope.launch(SINGLE_THREAD) { this@Container.transform() }
    }

    suspend fun reduce(reducer: STATE.() -> STATE) {
        withContext(SINGLE_THREAD) {
            _state.value = _state.value.reducer()
        }
    }

    suspend fun postEffect(event: EFFECT) {
        _effect.send(event)
    }

    companion object {
        val SINGLE_THREAD = newSingleThreadContext("mvi")
    }
}