package com.example.newsapplication.data.remotesource

import com.example.newsapplication.data.repositories.AppRepo
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.utils.*

class TopHeadlinesRemoteSource(private val appRepo: AppRepo) : TopHeadlinesDataSource {
    override suspend fun getTopHeadlines():Any?{
        var map = HashMap<String,String>()
        map.put(COUNTRY_PARAM_KEY, COUNTRY)
        map.put(API_PARAM_KEY, API_KEY)
        return appRepo.getCall(TOP_HEADLINES_URL,map)
    }
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