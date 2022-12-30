package com.github.af2905.movieland.core.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.github.af2905.movieland.core.data.database.entity.*
import com.github.af2905.movieland.core.data.database.entity.plain.MovieCreditsCast
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
class ListStringConverter @Inject constructor(gson: Gson) : ListConverter<String>(gson)

@ProvidedTypeConverter
class GenreConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<Genre>? {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(value, listType)
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
    fun toList(value: String?): List<ProductionCompany>? {
        val listType = object : TypeToken<List<ProductionCompany>>() {}.type
        return gson.fromJson(value, listType)
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
    fun toList(value: String?): List<ProductionCountry>? {
        val listType = object : TypeToken<List<ProductionCountry>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<ProductionCountry>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<ProductionCountry>>() {}.type)
    }
}

@ProvidedTypeConverter
class PersonMovieCreditsCastConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<PersonMovieCreditsCast>? {
        val listType = object : TypeToken<List<PersonMovieCreditsCast>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<PersonMovieCreditsCast>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<PersonMovieCreditsCast>>() {}.type)
    }
}

@ProvidedTypeConverter
class MovieCreditsCastConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<MovieCreditsCast>? {
        val listType = object : TypeToken<List<MovieCreditsCast>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<MovieCreditsCast>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<MovieCreditsCast>>() {}.type)
    }
}

@ProvidedTypeConverter
class MovieConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<Movie>? {
        val listType = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Movie>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<Movie>>() {}.type)
    }
}

/*@ProvidedTypeConverter
class TvShowConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<TvShow>? {
        val listType = object : TypeToken<List<TvShow>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<TvShow>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<TvShow>>() {}.type)
    }
}*/

@ProvidedTypeConverter
class CreatedByConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<CreatedBy>? {
        val listType = object : TypeToken<List<CreatedBy>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<CreatedBy>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<CreatedBy>>() {}.type)
    }
}

@ProvidedTypeConverter
class NetworkConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<Network>? {
        val listType = object : TypeToken<List<Network>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Network>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<Network>>() {}.type)
    }
}

@ProvidedTypeConverter
class SeasonConverter @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun toList(value: String?): List<Season>? {
        val listType = object : TypeToken<List<Season>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Season>?): String? {
        return if (list == null) null
        else gson.toJson(list, object : TypeToken<List<Season>>() {}.type)
    }
}
