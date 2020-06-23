package com.example.newsapplication.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines

interface TopHeadlinesDataSource {
    suspend fun getTopHeadlines(pageNumber:Int):Any?
    fun getTopHeadlinesLocally(): LiveData<List<Article>?>?
    suspend fun insertTopHeadlinesLocally(article: Article)
    suspend fun deleteTopHeadlines()
    suspend fun updateTotalResults(topHeadlines: TopHeadlines)
    fun getTotalResults(): LiveData<Int>?
    fun getLoadingLiveData(liveData: MutableLiveData<Boolean?>) : LiveData<Boolean?>?
}