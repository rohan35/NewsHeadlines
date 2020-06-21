package com.example.newsapplication.utils

import androidx.room.TypeConverter
import com.example.newsapplication.model.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object RoomTypeConverters {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun articleStringToList(data: String?): List<Article> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Article>>() {

        }.type

        return gson.fromJson<List<Article>>(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun articleListToString(channelList: List<Article>): String {
        return gson.toJson(channelList)
    }
}