package com.github.af2905.movieland.home.presentation.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.MovieItemV2
import com.github.af2905.movieland.home.HomeRepository
import com.github.af2905.movieland.home.domain.params.PopularMoviesParams
import com.github.af2905.movieland.home.domain.usecase.GetPopularMovies
import com.github.af2905.movieland.home.presentation.HomeNavigator
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<PopularMovieContract.State, PopularMovieContract.Effect> =
        Container(viewModelScope, PopularMovieContract.State.Init())

    init {
        loadData()
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            container.reduce {
                PopularMovieContract.State.Init(list = this.list)
            }
            val result = getPopularMovies(PopularMoviesParams(forceUpdate = forceUpdate))
            if (result.isFailure) {
                val error = result.exceptionOrNull()
                container.reduce {
                    PopularMovieContract.State.Error(
                        list = listOf(
                            ErrorItem(
                                errorMessage = error?.message.orEmpty(),
                                errorButtonVisible = false
                            )
                        ),
                        e = error
                    )
                }
            } else {
                val movies = result.getOrNull().orEmpty()
                container.reduce {
                    PopularMovieContract.State.Content(
                        list = movies.map { MovieItemV2(it) }
                    )
                }
            }
        }
    }

    private fun refresh() = loadData(forceUpdate = true)

    fun openDetail(itemId: Int) = navigateToDetail(itemId)

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(PopularMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}