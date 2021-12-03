package com.github.af2905.movieland.presentation.feature.search

import com.github.af2905.movieland.presentation.model.Model

sealed class SearchResult{
    object Loading : SearchResult()
    object EmptyResult : SearchResult()
    object EmptyQuery : SearchResult()
    data class SuccessResult(val result: List<Model>) : SearchResult()
    data class ErrorResult(val e: Throwable?) : SearchResult()
}