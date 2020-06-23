package com.example.newsapplication.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapplication.data.localsource.TopHeadlinesLocalSource
import com.example.newsapplication.data.remotesource.TopHeadlinesRemoteSource
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines

class TopHeadlinesRepo(private val topHeadlinesLocalSource: TopHeadlinesDataSource,
                       private val topHeadlinesRemoteSource: TopHeadlinesDataSource) {
    /**
     * get top headlines remotely
     */
    suspend fun getTopHeadlines(pageSize: Int) = topHeadlinesRemoteSource.getTopHeadlines(pageSize)

    /**
     * get top headlines locally
     */
    fun getTopHeadlinesLocally() = topHeadlinesLocalSource.getTopHeadlinesLocally()

    /**
     * Insert items into database
     */
    suspend fun insertTopHeadlines(article: Article) {
        topHeadlinesLocalSource.insertTopHeadlinesLocally(article)
    }

    suspend fun deleteTopHeadlines() {
        topHeadlinesLocalSource
    }

    suspend fun updateTotalResults(topHeadlines: TopHeadlines) {
        topHeadlinesLocalSource.updateTotalResults(topHeadlines)
    }

    fun getTotalResults(): LiveData<Int>? = topHeadlinesLocalSource.getTotalResults()

    fun getLoadingLiveData(liveData: MutableLiveData<Boolean?>): LiveData<Boolean?>? {
        return  TopHeadlinesRemoteSource.loadingLiveData
    }
}