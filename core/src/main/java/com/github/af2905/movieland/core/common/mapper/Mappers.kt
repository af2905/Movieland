package com.github.af2905.movieland.core.common.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}

interface MovieResponseMapper<I, T, D, O> {
    fun map(input: I, type: T, timeStamp: D): O
}

interface ListMapper<I, O> : Mapper<List<I>, List<O>>

interface MovieResponseListMapper<I, T, D, O> : MovieResponseMapper<List<I>, T, D, List<O>>

open class ListMapperImpl<I, O>(private val mapper: Mapper<I, O>) : ListMapper<I, O> {
    override fun map(input: List<I>): List<O> = input.map { mapper.map(it) }
}

open class MovieResponseListMapperImpl<I, T, D, O>(
    private val mapper: MovieResponseMapper<I, T, D, O>
) : MovieResponseListMapper<I, T, D, O> {
    override fun map(input: List<I>, type: T, timeStamp: D): List<O> =
        input.map { mapper.map(it, type, timeStamp) }
}