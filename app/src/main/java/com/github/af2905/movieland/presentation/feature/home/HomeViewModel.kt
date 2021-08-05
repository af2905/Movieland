package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.feature.home.item.MovieItem
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.widget.item.LoadingItem
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableLiveData<List<Model>>(listOf())
    val items: LiveData<List<Model>> = _items

    private fun starter() = listOf(LoadingItem())

    init {
        _items.postValue(starter())

        launchIO(
            params = PopularMoviesParams(),
            execute = getPopularMovies::invoke
        ) {
            MovieItem.map(it.movies).let { list ->
                _items.postValue(list + list + list + list)
            }
        }
    }
    
    fun openDetail() {
        navigate { forwardMovieDetail() }
    }
}