package com.github.af2905.movieland.helper.mapper

interface IMapper<I, O> {
    fun map(input: I): O
}

interface IListMapper<I, O> : IMapper<List<I>, List<O>>

interface INullableInputListMapper<I, O> : IMapper<List<I>?, List<O>>

interface INullableOutputListMapper<I, O> : IMapper<List<I>, List<O>?>