package com.github.af2905.movieland.presentation.feature.home.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.domain.usecase.movies.GetTopRatedMovies
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.DividerItem
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class TopMovieViewModel @Inject constructor(
    private val getTopRatedMovies: GetTopRatedMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider){

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    init {
        loadData()
    }

    private fun loadData(forced: Boolean = false) {
        launchUI {
            loading.emit(true)
            val topRated = loadTopRatedMoviesAsync(this, forced)
            _items.value = topRated.await().getOrDefault(emptyList())
            loading.emit(false)
        }
    }

    private suspend fun loadTopRatedMoviesAsync(
        coroutineScope: CoroutineScope,
        forced: Boolean
    ): Deferred<Result<List<Model>>> {
        val deferredTopRated = coroutineScope.iOAsync {
            val topRatedMovies =
                getTopRatedMovies(TopRatedMoviesParams(forced = forced)).getOrThrow().movies
                    ?.map { MovieItemVariant(it) } ?: emptyList()
            if (topRatedMovies.isNotEmpty()) {
                mutableListOf<Model>().apply {
                    topRatedMovies.map { addAll(listOf(it, DividerItem())) }
                }
            } else emptyList()
        }
        return deferredTopRated
    }

    fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }
}