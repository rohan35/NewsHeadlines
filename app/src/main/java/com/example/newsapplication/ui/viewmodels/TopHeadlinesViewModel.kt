package com.example.newsapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.Article
import com.example.newsapplication.network.NetworkResource
import com.example.newsapplication.utils.ERROR_OCCURRED
import com.example.newsapplication.utils.observeOnce

class TopHeadlinesViewModel(var topHeadlinesUseCase: TopHeadlinesUseCase) : ViewModel() {
    // error
    private var _errorLiveData = MutableLiveData<String>()
    val errorLiveData:LiveData<String>
    get() = _errorLiveData

        //list live data
    private var _articleListLiveData = MutableLiveData<List<Article>>()
    val articleListLiveData:LiveData<List<Article>>
    get() = _articleListLiveData

    //view loader live data
    var isLoading = MutableLiveData<Boolean>().apply { value = false }

    fun getTopHeadlines() {
        topHeadlinesUseCase.getTopHeadlines().observeOnce(Observer { networkResponse ->
            networkResponse?.let {
                when (networkResponse.status) {
                    NetworkResource.Status.SUCCESS -> {
                        networkResponse?.data?.let {
                            // show data to the UI
                            topHeadlines ->
                            _articleListLiveData.value = topHeadlines.articles
                        }
                    }
                    NetworkResource.Status.ERROR -> {
                        // show error
                        _errorLiveData.value = networkResponse.message?: ERROR_OCCURRED
                    }
                    else->
                    {

                    }
                }
            }
        })
    }
}