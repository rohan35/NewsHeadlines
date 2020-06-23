package com.example.newsapplication.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newsapplication.utils.RoomTypeConverters

@Entity(tableName = "topHeadlines")
data class TopHeadlines(
    @PrimaryKey(autoGenerate = true)
    var headlineId: Int? = null,
    @Ignore
    val articles: List<Article>? = null,
    @Ignore
    val status: String? = null,
    var totalResults: Int? = null
)
@Entity(tableName = "article")
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
):ComponentViewType(ComponentViewType.VIEW_TYPE_LIST)

data class Source(
    val id: Any,
    val name: String
)