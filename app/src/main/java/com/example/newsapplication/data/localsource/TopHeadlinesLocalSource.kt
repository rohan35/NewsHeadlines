package com.example.newsapplication.data.localsource

import com.example.newsapplication.database.TopHeadlinesDao
import com.example.newsapplication.interfaces.TopHeadlinesDataSource

class TopHeadlinesLocalSource(private var topHeadlinesDao: TopHeadlinesDao) : TopHeadlinesDataSource {
    override suspend fun getTopHeadlines(): Any {
        TODO("Not yet implemented")
    }
}