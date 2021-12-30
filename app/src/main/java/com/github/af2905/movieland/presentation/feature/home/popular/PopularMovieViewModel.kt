package com.github.af2905.movieland.presentation.feature.home.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.DividerItem
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    init {
        loadData()
        launchIO {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData() {
        launchUI {
            loading.emit(true)
            val popular = loadPopularMoviesAsync(this)
            _items.value = popular.await().getOrDefault(emptyList())
            loading.emit(false)
        }
    }

    private suspend fun loadPopularMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredPopular = coroutineScope.iOAsync {
            val popularMovies =
                getPopularMovies(PopularMoviesParams()).getOrThrow().movies
                    ?.map { MovieItemVariant(it) } ?: emptyList()
            if (popularMovies.isNotEmpty()) {
                mutableListOf<Model>().apply {
                    popularMovies.map { addAll(listOf(it, DividerItem())) }
                }
            } else emptyList()
        }
        return deferredPopular
    }

    private fun refresh() = loadData()

    fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }
}