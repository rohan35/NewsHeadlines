package com.example.newsapplication.interfaces

import com.example.newsapplication.model.TopHeadlines

interface TopHeadlinesDataSource {
    suspend fun getTopHeadlines():Any?
    suspend fun getTopHeadlinesLocally():TopHeadlines?
    suspend fun insertTopHeadlinesLocally(topHeadlines: TopHeadlines)
    suspend fun deleteTopHeadlines()
}