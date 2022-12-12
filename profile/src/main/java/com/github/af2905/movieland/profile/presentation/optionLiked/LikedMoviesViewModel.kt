package com.github.af2905.movieland.profile.presentation.optionLiked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.item.MovieItemVariant
import com.github.af2905.movieland.profile.usecase.GetAllSavedMovies
import com.github.af2905.movieland.profile.usecase.params.Params
import javax.inject.Inject

class LikedMoviesViewModel @Inject constructor(
    private val getAllSavedMovies: GetAllSavedMovies
) : ViewModel() {

    val container: Container<LikedMoviesContract.State, LikedMoviesContract.Effect> =
        Container(viewModelScope, LikedMoviesContract.State.Loading)

    init {
        loadData()
    }

    private fun loadData() {
        container.intent {
            val savedMovies = getAllSavedMovies.invoke(Params).getOrNull().orEmpty()
            container.reduce {
                LikedMoviesContract.State.Content(list = savedMovies.map { MovieItemVariant(it) })
            }
        }
    }

    fun openDetail(itemId: Int) {
        container.intent {
            container.postEffect(LikedMoviesContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as LikedMoviesNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}