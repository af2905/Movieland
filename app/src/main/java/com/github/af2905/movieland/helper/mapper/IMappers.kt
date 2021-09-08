package com.github.af2905.movieland.helper.mapper

interface IMapper<I, O> {
    fun map(input: I): O
}

interface IMovieResponseMapper<I, T, O> {
    fun map(input: I, type: T): O
}

interface IListMapper<I, O> : IMapper<List<I>, List<O>>

interface IMovieResponseListMapper<I, T, O> : IMovieResponseMapper<List<I>, T, List<O>>

interface INullableInputListMapper<I, O> : IMapper<List<I>?, List<O>>

interface INullableOutputListMapper<I, O> : IMapper<List<I>, List<O>?>