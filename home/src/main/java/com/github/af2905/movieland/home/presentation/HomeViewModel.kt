package com.github.af2905.movieland.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.GenreType
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.PersonType
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.repository.GenresRepository
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import com.github.af2905.movieland.core.repository.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
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

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            // Fetch Trending Data
            val trendingData = combine(
                trendingRepository.getTrendingMovies(MovieType.TRENDING_DAY, null, 1)
                    .catch { emit(emptyList()) },
                trendingRepository.getTrendingTvShows(TvShowType.TRENDING_DAY, null, 1)
                    .catch { emit(emptyList()) },
                trendingRepository.getTrendingPeople(PersonType.TRENDING_DAY, null, 1)
                    .catch { emit(emptyList()) }
            ) { trendingMovies, trendingTvShows, trendingPeople ->
                Triple(trendingMovies, trendingTvShows, trendingPeople)
            }

            // Fetch Movies Data
            val movieData = combine(
                moviesRepository.getMovies(MovieType.POPULAR, null, 1),
                moviesRepository.getMovies(MovieType.TOP_RATED, null, 1),
                moviesRepository.getMovies(MovieType.UPCOMING, null, 1),
                moviesRepository.getMovies(MovieType.NOW_PLAYING, null, 1)
            ) { popular, topRated, upcoming, nowPlaying ->
                listOf(popular, topRated, upcoming, nowPlaying)
            }

            // Fetch TV Show Data
            val tvShowData = combine(
                tvShowsRepository.getTvShows(TvShowType.POPULAR, null, 1)
                    .catch { emit(emptyList()) },
                tvShowsRepository.getTvShows(TvShowType.TOP_RATED, null, 1)
                    .catch { emit(emptyList()) }
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
                trendingData,
                movieData,
                tvShowData,
                genresData,
                peopleData
            ) { trending, movies, tvShows, genres, popularPeople ->
                val (trendingMovies, trendingTvShows, trendingPeople) = trending
                val (popularMovies, topRatedMovies, upcomingMovies, nowPlayingMovies) = movies
                val (popularTvShows, topRatedTvShows) = tvShows
                val (movieGenres, tvGenres) = genres

                val allMoviesEmpty =
                    (popularMovies as? ResultWrapper.Success)?.data.isNullOrEmpty() &&
                            (topRatedMovies as? ResultWrapper.Success)?.data.isNullOrEmpty() &&
                            (upcomingMovies as? ResultWrapper.Success)?.data.isNullOrEmpty() &&
                            (nowPlayingMovies as? ResultWrapper.Success)?.data.isNullOrEmpty()

                state = if (allMoviesEmpty) {
                    state.copy(
                        isLoading = false,
                        isError = true
                    )
                } else {
                    state.copy(
                        trendingMovies = trendingMovies,
                        trendingTvShows = trendingTvShows,
                        trendingPeople = trendingPeople,
                        popularMovies = if (popularMovies is ResultWrapper.Success) popularMovies.data else state.popularMovies,
                        topRatedMovies = if (topRatedMovies is ResultWrapper.Success) topRatedMovies.data else state.topRatedMovies,
                        upcomingMovies = if (upcomingMovies is ResultWrapper.Success) upcomingMovies.data else state.upcomingMovies,
                        nowPlayingMovies = if (nowPlayingMovies is ResultWrapper.Success) nowPlayingMovies.data else state.nowPlayingMovies,
                        moviesGenres = movieGenres,
                        tvShowsGenres = tvGenres,
                        popularPeople = popularPeople,
                        popularTvShows = popularTvShows,
                        topRatedTvShows = topRatedTvShows,
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
    }
}