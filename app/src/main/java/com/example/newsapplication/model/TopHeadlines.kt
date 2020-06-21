package com.example.newsapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newsapplication.utils.RoomTypeConverters

@Entity(tableName = "topHeadlines")
data class TopHeadlines(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @TypeConverters(RoomTypeConverters::class)
    val articles: List<Article>,
    val status: String? = null,
    val totalResults: Int? = null
)

data class Article(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

data class Source(
    val id: Any,
    val name: String
)