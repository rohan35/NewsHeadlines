package com.example.newsapplication.data.remotesource

import com.example.newsapplication.data.repositories.AppRepo
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.utils.TOP_HEADLINES_URL

class TopHeadlinesRemoteSource(private val appRepo: AppRepo) : TopHeadlinesDataSource {
    override suspend fun getTopHeadlines() = appRepo.getCall(TOP_HEADLINES_URL)
    override suspend fun getTopHeadlinesLocally(): TopHeadlines? {
        // no implementation
        return null
    }

    override suspend fun insertTopHeadlinesLocally(topHeadlines: TopHeadlines) {
        // no implementation
    }

     override suspend fun deleteTopHeadlines() {
          //No implementation
     }

}