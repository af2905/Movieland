package com.github.af2905.movieland.core.pager.tv_shows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.repository.TvShowsRepository

class SimilarOrRecommendedTvShowsPagingSource(
    private val tvShowsRepository: TvShowsRepository,
    private val tvShowId: Int,
    private val tvShowType: TvShowType,
    private val language: String?
) : PagingSource<Int, TvShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val currentPage = params.key ?: 1

            val responseFlow = when (tvShowType) {
                TvShowType.RECOMMENDED -> tvShowsRepository.getRecommendedTvShows(tvShowId, language, currentPage)
                TvShowType.SIMILAR -> tvShowsRepository.getSimilarTvShows(tvShowId, language, currentPage)
                else -> throw IllegalArgumentException("Invalid tvShowType for recommended/similar")
            }

            var result: ResultWrapper<List<TvShow>>? = null
            responseFlow.collect { response ->
                result = response
            }

            // Ensure we actually received data before proceeding
            result?.let { response ->
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

                    is ResultWrapper.Loading -> {
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
                        )
                    }
                }
            } ?: throw Exception("No response received") // If nothing was collected, fail explicitly

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
