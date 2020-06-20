package com.example.newsapplication.interfaces

interface TopHeadlinesDataSource {
    suspend fun getTopHeadlines():Any?
}