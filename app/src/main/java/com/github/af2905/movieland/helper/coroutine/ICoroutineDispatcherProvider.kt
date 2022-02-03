package com.github.af2905.movieland.helper.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface ICoroutineDispatcherProvider {

    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun immediate(): CoroutineDispatcher

}

class CoroutineDispatcherProvider @Inject constructor() : ICoroutineDispatcherProvider {

    override fun main() = Dispatchers.Main
    override fun io() = Dispatchers.IO
    override fun default() = Dispatchers.Default
    override fun immediate() = Dispatchers.Main.immediate

}
