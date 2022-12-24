package com.github.af2905.movieland.profile.presentation.optionLiked.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.item.MovieItemV2
import com.github.af2905.movieland.core.common.model.item.SimpleTextItem
import com.github.af2905.movieland.profile.R
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

            val list = if (savedMovies.isNotEmpty()) {
                savedMovies.map { MovieItemV2(it) }
            } else {
                listOf(SimpleTextItem(res = R.string.liked_movies_empty_list_text))
            }

            container.reduce {
                LikedMoviesContract.State.Content(list = list)
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