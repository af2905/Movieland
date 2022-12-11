package com.github.af2905.movieland.core.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.ProductionCountry
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
class GenreConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun fromString(json: String?): List<Genre> {
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<Genre>>() {}.type)
        } else {
            emptyList()
        }
    }

    @TypeConverter
    fun fromList(list: List<Genre>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<Genre>>() {}.type)
    }
}

@ProvidedTypeConverter
class ProductionCompanyConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun fromString(json: String?): List<ProductionCompany> {
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<ProductionCompany>>() {}.type)
        } else {
            emptyList()
        }
    }

    @TypeConverter
    fun fromList(list: List<ProductionCompany>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<ProductionCompany>>() {}.type)
    }
}

@ProvidedTypeConverter
class ProductionCountryConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun fromString(json: String?): List<ProductionCountry> {
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<ProductionCountry>>() {}.type)
        } else {
            emptyList()
        }
    }

    @TypeConverter
    fun fromList(list: List<ProductionCountry>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<ProductionCountry>>() {}.type)
    }
}





