package com.example.newsapplication.data.remotesource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapplication.data.repositories.AppRepo
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.utils.*

class TopHeadlinesRemoteSource(private val appRepo: AppRepo) : TopHeadlinesDataSource {


    companion object
    {
        var loadingLiveData:MutableLiveData<Boolean>? = MutableLiveData()
    }

    override suspend fun getTopHeadlines(pageNumber:Int):Any?{
        var map = HashMap<String,String>()
        map.put(COUNTRY_PARAM_KEY, COUNTRY)
        map.put(PAGE_NUMBER_PARAM_KEY,pageNumber.toString())
        map.put(PAGE_SIZE_PARAM_KEY,"10")
        map.put(API_PARAM_KEY, API_KEY)
        if (pageNumber == 1) {
           loadingLiveData?.postValue(true)
        } else {
            loadingLiveData?.postValue(false)
        }
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

    override suspend fun updateTotalResults( topHeadlines: TopHeadlines) {
        // no implementation
    }

    override fun getTotalResults(): LiveData<Int>? {
       return null
    }

    override fun getLoadingLiveData(liveData: MutableLiveData<Boolean?>):LiveData<Boolean?>? {
        return loadingLiveData
    }

}