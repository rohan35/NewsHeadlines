package com.example.newsapplication.data.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.ComponentViewType
import kotlinx.coroutines.CoroutineScope

interface TopHeadlinesUseCase {
    fun getTopHeadlines(): LiveData<List<Article>?>?
    fun runWorkManagerTask(pageNumber:Int)
    fun getTotalResults():LiveData<Int>?
    fun getLoadingLiveData(liveData: MutableLiveData<Boolean?>):LiveData<Boolean?>?
    fun mergeAdapterList(
        scope: CoroutineScope,
        articleListLiveData: MutableLiveData<ArrayList<ComponentViewType>>,
        articleList: MutableList<Article>,
        removeLoader: Boolean?
    )
}