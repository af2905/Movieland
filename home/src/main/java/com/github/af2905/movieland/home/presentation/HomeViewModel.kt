package com.github.af2905.movieland.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            // Combine trending data
            val trendingData = combine(
                trendingRepository.getTrendingMovies(MovieType.TRENDING_DAY, null, 1),
                trendingRepository.getTrendingTvShows(TvShowType.TRENDING_DAY, null, 1),
                trendingRepository.getTrendingPeople(PersonType.TRENDING_DAY, null, 1)
            ) { trendingMovies, trendingTvShows, trendingPeople ->
                Triple(trendingMovies, trendingTvShows, trendingPeople)
            }

            // Combine movie data
            val movieData = combine(
                moviesRepository.getMovies(MovieType.POPULAR, null, 1),
                moviesRepository.getMovies(MovieType.TOP_RATED, null, 1),
                moviesRepository.getMovies(MovieType.UPCOMING, null, 1),
                moviesRepository.getMovies(MovieType.NOW_PLAYING, null, 1)
            ) { popularMovies, topRatedMovies, upcomingMovies, nowPlayingMovies ->
                listOf(popularMovies, topRatedMovies, upcomingMovies, nowPlayingMovies)
            }

            // Combine TV show data
            val tvShowData = combine(
                tvShowsRepository.getTvShows(TvShowType.POPULAR, null, 1),
                tvShowsRepository.getTvShows(TvShowType.TOP_RATED, null, 1)
            ) { popularTvShows, topRatedTvShows ->
                Pair(popularTvShows, topRatedTvShows)
            }

            // Combine genres
            val genresData = combine(
                genresRepository.getGenres(GenreType.MOVIE, null),
                genresRepository.getGenres(GenreType.TV_SHOW, null)
            ) { movieGenres, tvGenres ->
                Pair(movieGenres, tvGenres)
            }

            val peopleData = peopleRepository.getPopularPeople(null)

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

                state.copy(
                    trendingMovies = trendingMovies,
                    trendingTvShows = trendingTvShows,
                    trendingPeople = trendingPeople,
                    popularMovies = popularMovies,
                    topRatedMovies = topRatedMovies,
                    upcomingMovies = upcomingMovies,
                    nowPlayingMovies = nowPlayingMovies,
                    moviesGenres = movieGenres,
                    tvShowsGenres = tvGenres,
                    popularPeople = popularPeople,
                    popularTvShows = popularTvShows,
                    topRatedTvShows = topRatedTvShows,

                    )
            }
                .catch { throwable ->
                    // Handle errors
                    state = state.copy(/* Add error handling here */)
                }
                .collect { updatedState ->
                    state = updatedState
                }
        }
    }


    fun onAction(action: HomeAction) = when (action) {
        is HomeAction.OpenMovieDetail -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToMovieDetail(action.movieId))
            }
        }

        is HomeAction.OpenTvShowDetail -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToTvShowDetail(action.tvShowId))
            }
        }

        is HomeAction.OpenPersonDetail -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToPersonDetail(action.personId))
            }
        }

        is HomeAction.OpenGenre -> {
            viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToGenre(action.genreId))
            }
        }
    }
}