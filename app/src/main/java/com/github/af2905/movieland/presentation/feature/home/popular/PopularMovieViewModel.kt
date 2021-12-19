package com.github.af2905.movieland.presentation.feature.home.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.DividerItem
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    init {
        loadData()
    }

    private fun loadData(forced: Boolean = false) {
        launchUI {
            loading.emit(true)
            val popular = loadPopularMoviesAsync(this, forced)
            _items.value = popular.await().getOrDefault(emptyList())
            loading.emit(false)
        }
    }

    private suspend fun loadPopularMoviesAsync(
        coroutineScope: CoroutineScope,
        forced: Boolean
    ): Deferred<Result<List<Model>>> {
        val deferredPopular = coroutineScope.iOAsync {
            val popularMovies =
                getPopularMovies(PopularMoviesParams(forced = forced)).getOrThrow().movies
                    ?.map { MovieItemVariant(it) } ?: emptyList()
            if (popularMovies.isNotEmpty()) {
                mutableListOf<Model>().apply {
                    popularMovies.map { addAll(listOf(it, DividerItem())) }
                }
            } else emptyList()
        }
        return deferredPopular
    }

    fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }
}