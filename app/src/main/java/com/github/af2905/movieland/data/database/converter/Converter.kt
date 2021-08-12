package com.github.af2905.movieland.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val SEPARATOR = ","

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

//class GenreIdsConverter @Inject constructor(gson: Gson) : ListConverter<Int>(gson)


class GenreIdsConverter {
    @TypeConverter
    fun fromList(ids: List<Int>?) = ids?.joinToString(separator = SEPARATOR) { it.toString() }

    @TypeConverter
    fun toList(ids: String?) = ids?.split(SEPARATOR)
}


/*class MovieEntityConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromList(list: List<MovieEntity>?): String? {
            if (list == null) return null
            return Gson().toJson(list, object : TypeToken<List<MovieEntity>>() {}.type)
        }

        @TypeConverter
        @JvmStatic
        fun toList(json: String?): List<MovieEntity> {
            if (json == null) return emptyList()
            return Gson().fromJson(json, object : TypeToken<List<MovieEntity>>() {}.type)
        }
    }
}*/


/*public class DataConverter implements Serializable {

 @TypeConverter // note this annotation
    public String fromOptionValuesList(List<OptionValues> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<OptionValues>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<OptionValues> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<OptionValues>>() {
        }.getType();
        List<OptionValues> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}*/


/*class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromInstant(value: Instant): Long {
            return value.toEpochMilli()
        }

        @TypeConverter
        @JvmStatic
        fun toInstant(value: Long): Instant {
            return Instant.ofEpochMilli(value)
        }
    }
}*/

/*public class SpecialtyConverter {
    @TypeConverter
    public static String listToJson(List<Specialty> specialties) {
        if (specialties == null) {
            return null;
        }
        Type type = new TypeToken<List<Specialty>>() {
        }.getType();
        return new Gson().toJson(specialties, type);
    }

    @TypeConverter
    public static List<Specialty> jsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Specialty>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}*/









