package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.feature.home.item.MovieItem
import com.github.af2905.movieland.presentation.model.Model
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    //private val navigator: HomeNavigator,
    private val getPopularMovies: GetPopularMovies
) : BaseViewModel() {

    private val _items = MutableLiveData<List<Model>>(listOf())
    val items: LiveData<List<Model>> = _items

    //fun openMovieDetail() = navigator.forwardMovieDetail()

    init {
        launchIO(
            params = PopularMoviesParams(),
            execute = getPopularMovies::invoke
        ) {
            MovieItem.map(it.movies).let {list ->
                _items.postValue(list + list + list + list)
            }
        }
    }
}