package com.example.newsapplication.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newsapplication.utils.RoomTypeConverters

data class TopHeadlines(
    val id: Int? = null,
    @TypeConverters(RoomTypeConverters::class)
    val articles: List<Article>,
    val status: String? = null,
    val totalResults: Int? = null
)
@Entity(tableName = "topHeadlines")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val autoId: Int? = null,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    @TypeConverters(RoomTypeConverters::class)
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

data class Source(
    val id: Any,
    val name: String
)