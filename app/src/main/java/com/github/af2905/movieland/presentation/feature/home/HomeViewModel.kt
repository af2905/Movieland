package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.*
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val forceUpdate: ForceUpdate,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    val header = HeaderItem(R.string.now_playing)

    private val _headerVisible = MutableLiveData(false)
    val headerVisible = _headerVisible

    private val _nowPlayingMovies = MutableLiveData<List<Model>>()
    val nowPlayingMovies: LiveData<List<Model>> = _nowPlayingMovies

    private val _updateFailed = MutableLiveData(false)
    val updateFailed = _updateFailed

    init {
        loadData()
        launchIO {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData() {
        launchUI {
            loading.emit(true)
            val nowPlayingList = mutableListOf<Model>()
            val nowPlaying = loadNowPlayingMoviesAsync(this)
            nowPlayingList.addAll(nowPlaying.await().getOrThrow())
            _nowPlayingMovies.value = nowPlayingList
            _headerVisible.value = nowPlayingList.isNotEmpty()
            loading.emit(false)
        }
    }

    private suspend fun loadNowPlayingMoviesAsync(coroutineScope: CoroutineScope)
            : Deferred<Result<List<Model>>> {
        val deferredNowPlaying = coroutineScope.iOAsync {
            return@iOAsync getNowPlayingMovies(NowPlayingMoviesParams())
                .getOrThrow().movies ?: emptyList()
        }
        return deferredNowPlaying
    }

    fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }


    fun setForceUpdate() {
        launchIO { forceUpdate.invoke(Unit) }
    }

    private fun refresh() = loadData()

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        _updateFailed.postValue(true)
    }
}