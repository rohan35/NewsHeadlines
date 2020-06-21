package com.example.newsapplication.data.remotesource

import androidx.lifecycle.LiveData
import com.example.newsapplication.data.repositories.AppRepo
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.utils.*

class TopHeadlinesRemoteSource(private val appRepo: AppRepo) : TopHeadlinesDataSource {
    override suspend fun getTopHeadlines(pageNumber:Int):Any?{
        var map = HashMap<String,String>()
        map.put(COUNTRY_PARAM_KEY, COUNTRY)
        map.put(PAGE_NUMBER_PARAM_KEY,pageNumber.toString())
        map.put(PAGE_SIZE_PARAM_KEY,"10")
        map.put(API_PARAM_KEY, API_KEY)
        return appRepo.getCall(TOP_HEADLINES_URL,map)
    }
    override fun getTopHeadlinesLocally(): LiveData<List<Article>?>? {
        // no implementation
        return null
    }

    override suspend fun insertTopHeadlinesLocally(article: Article) {
        // no implementation
    }

     override suspend fun deleteTopHeadlines() {
          //No implementation
     }

}