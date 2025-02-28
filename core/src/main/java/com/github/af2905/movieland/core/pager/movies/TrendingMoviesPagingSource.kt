package com.github.af2905.movieland.core.pager.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.repository.TrendingRepository
import kotlinx.coroutines.flow.first
class TrendingMoviesPagingSource(
    private val trendingRepository: TrendingRepository,
    private val movieType: MovieType,
    private val language: String?
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1

            val response = trendingRepository.getTrendingMovies(movieType, language, currentPage)
                .first { it is ResultWrapper.Success || it is ResultWrapper.Error }

            when (response) {
                is ResultWrapper.Success -> {
                    LoadResult.Page(
                        data = response.data,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (response.data.isEmpty()) null else currentPage + 1
                    )
                }

                is ResultWrapper.Error -> {
                    LoadResult.Error(response.throwable ?: Exception("Unknown error"))
                }

                else -> {
                    LoadResult.Error(Exception("Unexpected loading state"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}