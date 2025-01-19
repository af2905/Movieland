package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonType
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun getTrendingMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<List<Movie>>

    fun getTrendingTvShows(
        tvShowType: TvShowType,
        language: String?,
        page: Int?
    ): Flow<List<TvShow>>

    fun getTrendingPeople(
        personType: PersonType,
        language: String?,
        page: Int?
    ): Flow<List<Person>>
}