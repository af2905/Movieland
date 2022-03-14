package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.helper.extension.launchCollect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    private val _forceUpdate =
        MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    private val forceUpdate = _forceUpdate.asSharedFlow()

    private val _popularMoviesUploaded =
        MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    private val _topRatedMoviesUploaded =
        MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    private val _upcomingMoviesUploaded =
        MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    private val _nowPlayingMoviesUploaded =
        MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)

    private val _allMoviesUploaded =
        combine(
            _popularMoviesUploaded,
            _topRatedMoviesUploaded,
            _upcomingMoviesUploaded,
            _nowPlayingMoviesUploaded
        ) { popular, topRated, upcoming, nowPlaying -> popular && topRated && upcoming && nowPlaying }

    private var job: Job? = null

    override fun subscribeOnForceUpdate(
        scope: CoroutineScope,
        collector: suspend (force: Boolean) -> Unit
    ) {
        job = forceUpdate.launchCollect(scope, collector)
    }

    override fun forceUpdate() {
        _forceUpdate.tryEmit(true)
    }

    override fun popularMoviesUploaded() {
        _popularMoviesUploaded.tryEmit(true)
    }

    override fun topRatedMoviesUploaded() {
        _topRatedMoviesUploaded.tryEmit(true)
    }

    override fun upcomingMoviesUploaded() {
        _upcomingMoviesUploaded.tryEmit(true)
    }

    override fun nowPlayingMoviesUploaded() {
        _nowPlayingMoviesUploaded.tryEmit(true)
    }

    override fun subscribeOnAllMoviesUploaded(
        scope: CoroutineScope,
        collector: suspend (uploaded: Boolean) -> Unit
    ) {
        _allMoviesUploaded.shareIn(
            scope = scope,
            started = SharingStarted.Lazily
        ).launchCollect(scope, collector)
    }
}

interface HomeRepository {
    fun forceUpdate()
    fun subscribeOnForceUpdate(
        scope: CoroutineScope,
        collector: suspend (force: Boolean) -> Unit
    )

    fun popularMoviesUploaded()
    fun topRatedMoviesUploaded()
    fun upcomingMoviesUploaded()
    fun nowPlayingMoviesUploaded()
    fun subscribeOnAllMoviesUploaded(
        scope: CoroutineScope,
        collector: suspend (uploaded: Boolean) -> Unit
    )
}