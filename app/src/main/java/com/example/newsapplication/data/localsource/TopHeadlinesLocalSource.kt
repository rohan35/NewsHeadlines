package com.example.newsapplication.data.localsource

import com.example.newsapplication.database.TopHeadlinesDao
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.TopHeadlines

class TopHeadlinesLocalSource(private var topHeadlinesDao: TopHeadlinesDao) :
    TopHeadlinesDataSource {
    override suspend fun getTopHeadlines(): Any? {
        // nothing will be implemented
        return null
    }

    override suspend fun getTopHeadlinesLocally(): TopHeadlines? = topHeadlinesDao.getTopHeadlines()

    override suspend fun insertTopHeadlinesLocally(topHeadlines: TopHeadlines) {
        topHeadlinesDao.insert(topHeadlines)
    }

    override suspend fun deleteTopHeadlines() {
        topHeadlinesDao.deleteAll()
    }

}