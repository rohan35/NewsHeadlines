package com.example.newsapplication.utils

import androidx.room.TypeConverter
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object RoomTypeConverters {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun sourceStringToObject(data: String?): Source? {
        if (data == null)
            return null
        val type = object : TypeToken<Source>() {

        }.type

        return gson.fromJson<Source>(data, type)
    }

    @TypeConverter
    @JvmStatic
    fun sourceObjectToString(data: Source): String {
        return gson.toJson(data)
    }
}