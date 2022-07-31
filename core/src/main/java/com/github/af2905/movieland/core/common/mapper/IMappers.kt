package com.github.af2905.movieland.core.common.mapper

interface IMapper<I, O> {
    fun map(input: I): O
}

interface IMovieResponseMapper<I, T, D, O> {
    fun map(input: I, type: T, timeStamp: D): O
}

interface IListMapper<I, O> : IMapper<List<I>, List<O>>

interface IMovieResponseListMapper<I, T, D, O> : IMovieResponseMapper<List<I>, T, D, List<O>>

interface INullableInputListMapper<I, O> : IMapper<List<I>?, List<O>>

interface INullableOutputListMapper<I, O> : IMapper<List<I>, List<O>?>