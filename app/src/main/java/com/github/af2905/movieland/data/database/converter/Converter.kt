package com.github.af2905.movieland.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.github.af2905.movieland.data.database.entity.GenreEntity
import com.github.af2905.movieland.data.database.entity.ProductionCompanyEntity
import com.github.af2905.movieland.data.database.entity.ProductionCountryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

open class ListConverter<I>(private val gson: Gson) {
    @TypeConverter
    fun fromList(input: List<I>?): String? {
        return if (input == null) null
        else gson.toJson(input, object : TypeToken<List<I>>() {}.type)
    }

    @TypeConverter
    fun toList(json: String?): List<I> {
        return if (json == null) emptyList()
        else gson.fromJson(json, object : TypeToken<List<I>>() {}.type)
    }
}

@ProvidedTypeConverter
class ListIntConverter @Inject constructor(gson: Gson) : ListConverter<Int>(gson)

@ProvidedTypeConverter
class GenreConverter @Inject constructor(gson: Gson) : ListConverter<GenreEntity>(gson)

@ProvidedTypeConverter
class ProductionCompanyConverter @Inject constructor(gson: Gson) :
    ListConverter<ProductionCompanyEntity>(gson)

@ProvidedTypeConverter
class ProductionCountryConverter @Inject constructor(gson: Gson) :
    ListConverter<ProductionCountryEntity>(gson)










