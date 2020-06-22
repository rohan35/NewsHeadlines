package com.example.newsapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.network.NetworkResource
import com.example.newsapplication.utils.ERROR_OCCURRED
import com.example.newsapplication.utils.observeOnce

class TopHeadlinesViewModel(private var topHeadlinesUseCase: TopHeadlinesUseCase) : ViewModel() {
    // error
    private var _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    var pageNumber = 1
    //list live data
    var articleList = mutableListOf<Article>()

    var adapterPadding = MutableLiveData<Boolean>().apply { value = false }

    //view loader live data
    private val mIsLoading: MutableLiveData<Boolean?> = MutableLiveData()

    fun getTopHeadlines(): LiveData<List<Article>?>? {
        return topHeadlinesUseCase.getTopHeadlines()
    }

    fun runWorkManager(adapterPosition: Int) {
        topHeadlinesUseCase.getTotalResults()?.observeOnce(Observer {totalResults->
            if(adapterPosition <= totalResults?:0) {
                adapterPadding.value = true
                setIsLoading(true)
                pageNumber = adapterPosition.div(10) + 1
                topHeadlinesUseCase.runWorkManagerTask(pageNumber)
            }
            else{
                adapterPadding.value = false
            }
        })
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getIsLoading(): MutableLiveData<Boolean?>? {
        return mIsLoading
    }

    fun getArticle(autoId:Int): Article?
    {
       return articleList.find {
            it.autoId == autoId
        }
    }
}