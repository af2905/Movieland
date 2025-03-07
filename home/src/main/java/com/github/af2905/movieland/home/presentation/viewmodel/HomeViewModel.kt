package com.github.af2905.movieland.home.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.GenreType
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.PersonType
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.repository.GenresRepository
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import com.github.af2905.movieland.core.repository.TvShowsRepository
import com.github.af2905.movieland.home.presentation.state.HomeAction
import com.github.af2905.movieland.home.presentation.state.HomeEffect
import com.github.af2905.movieland.home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository,
    private val trendingRepository: TrendingRepository,
    private val genresRepository: GenresRepository,
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    private val trendingMoviesSharedFlow: StateFlow<ResultWrapper<List<Movie>>> = trendingRepository.getCachedFirstTrendingMovies(
        movieType = MovieType.TRENDING_DAY,
        language = null,
        page = null
    ).stateIn(scope = viewModelScope, started = Eagerly, initialValue = ResultWrapper.Loading)

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {

        viewModelScope.launch {
            state = state.copy(isLoading = true)

            // Trending Data
            launch {
                trendingMoviesSharedFlow.collectLatest { result ->
                    state = state.copy(
                        trendingMovies = if (result is ResultWrapper.Success) result.data else state.trendingMovies
                    )
                }
            }

            launch {
                trendingRepository.getTrendingTvShows(TvShowType.TRENDING_DAY, null, null)
                    .catch { emit(emptyList()) }.collectLatest { data ->
                        state = state.copy(trendingTvShows = data)
                    }
            }

            launch {
                trendingRepository.getTrendingPeople(PersonType.TRENDING_DAY, null, null)
                    .catch { emit(emptyList()) }.collectLatest { data ->
                        state = state.copy(trendingPeople = data)
                    }
            }

            // Fetch Movies Data
            val movieData = combine(
                moviesRepository.getCachedFirstMovies(MovieType.POPULAR, null, null),
                moviesRepository.getCachedFirstMovies(MovieType.TOP_RATED, null, null),
                moviesRepository.getCachedFirstMovies(MovieType.UPCOMING, null, null),
                moviesRepository.getCachedFirstMovies(MovieType.NOW_PLAYING, null, null)
            ) { popular, topRated, upcoming, nowPlaying ->
                listOf(popular, topRated, upcoming, nowPlaying)
            }

            // Fetch TV Show Data
            val tvShowData = combine(
                tvShowsRepository.getTvShows(TvShowType.POPULAR, null, null),
                tvShowsRepository.getTvShows(TvShowType.TOP_RATED, null, null)
            ) { popularTvShows, topRatedTvShows ->
                Pair(popularTvShows, topRatedTvShows)
            }

            // Fetch Genres
            val genresData = combine(
                genresRepository.getGenres(GenreType.MOVIE, null)
                    .catch { emit(emptyList()) },
                genresRepository.getGenres(GenreType.TV_SHOW, null)
                    .catch { emit(emptyList()) }
            ) { movieGenres, tvGenres ->
                Pair(movieGenres, tvGenres)
            }

            val peopleData = peopleRepository.getPopularPeople(null)
                .catch { emit(emptyList()) }

            // Combine all grouped data
            combine(
                movieData,
                tvShowData,
                genresData,
                peopleData
            ) { movies, tvShows, genres, popularPeople ->

                val (popularMovies, topRatedMovies, upcomingMovies, nowPlayingMovies) = movies
                val (popularTvShows, topRatedTvShows) = tvShows
                val (movieGenres, tvGenres) = genres

                val allMoviesError =
                    (popularMovies as? ResultWrapper.Error) != null &&
                            (topRatedMovies as? ResultWrapper.Error) != null &&
                            (upcomingMovies as? ResultWrapper.Error) != null &&
                            (nowPlayingMovies as? ResultWrapper.Error) != null

                state = if (allMoviesError) {
                    state.copy(
                        isLoading = false,
                        isError = true
                    )
                } else {
                    state.copy(
                        popularMovies = if (popularMovies is ResultWrapper.Success) popularMovies.data else state.popularMovies,
                        topRatedMovies = if (topRatedMovies is ResultWrapper.Success) topRatedMovies.data else state.topRatedMovies,
                        upcomingMovies = if (upcomingMovies is ResultWrapper.Success) upcomingMovies.data else state.upcomingMovies,
                        nowPlayingMovies = if (nowPlayingMovies is ResultWrapper.Success) nowPlayingMovies.data else state.nowPlayingMovies,
                        moviesGenres = movieGenres,
                        tvShowsGenres = tvGenres,
                        popularPeople = popularPeople,
                        popularTvShows = if (popularTvShows is ResultWrapper.Success) popularTvShows.data else state.popularTvShows,
                        topRatedTvShows = if (topRatedTvShows is ResultWrapper.Success) topRatedTvShows.data else state.topRatedTvShows,
                        isLoading = false,
                        isError = false,
                    )
                }
            }.collect {}
        }
    }

    fun onAction(action: HomeAction) = when (action) {
        is HomeAction.OpenMovieDetail -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToMovieDetail(movieId = action.movieId))
            }
        }

        is HomeAction.OpenTvShowDetail -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToTvShowDetail(tvShowId = action.tvShowId))
            }
        }

        is HomeAction.OpenPersonDetail -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToPersonDetail(personId = action.personId))
            }
        }

        is HomeAction.OpenGenre -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToGenre(genreId = action.genreId))
            }
        }

        is HomeAction.ChangeAppColor -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.ChangeAppColor(selectedTheme = action.selectedTheme))
            }
        }

        is HomeAction.RetryFetch -> {
            viewModelScope.launch {
                fetchHomeData()
            }
        }

        is HomeAction.OpenMoviesByType -> {
            viewModelScope.launch { _effect.send(HomeEffect.NavigateToMovies(movieType = action.movieType)) }
        }

        is HomeAction.OpenTvShowsByType -> {
            viewModelScope.launch { _effect.send(HomeEffect.NavigateToTvShows(tvShowType = action.tvShowType)) }
        }
    }
}