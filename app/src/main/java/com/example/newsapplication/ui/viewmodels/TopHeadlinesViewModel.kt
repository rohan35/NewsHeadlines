package com.example.newsapplication.ui.viewmodels

import androidx.lifecycle.*
import com.example.newsapplication.NewsApplication
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.ComponentViewType
import com.example.newsapplication.network.NetworkUtils
import com.example.newsapplication.utils.NO_INTERNET
import com.example.newsapplication.utils.observeOnce

class TopHeadlinesViewModel(private var topHeadlinesUseCase: TopHeadlinesUseCase) : ViewModel() {
    // error
    private var _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    //list live data
    var articleList = mutableListOf<Article>()

    var removeLoader = MutableLiveData<Boolean>().apply { value = false }

    //view loader live data
    private val mIsLoading: MutableLiveData<Boolean?> = MutableLiveData()

    // adapterlist
     val adapterListLiveData= MutableLiveData<ArrayList<ComponentViewType>>()

    fun getTopHeadlines(): LiveData<List<Article>?>? {
        return topHeadlinesUseCase.getTopHeadlines()
    }

    /*
    Method to execte task on background uusing data base or work manager
    @param - current position
     */
    fun runWorkManager(adapterPosition: Int) {
        topHeadlinesUseCase.getTotalResults()?.observeOnce(Observer { totalResults ->
            if (adapterPosition <= totalResults ?: 0) {
                removeLoader.value = false
                var pageNumber = adapterPosition.div(10) + 1
                if (NetworkUtils.verifyAvailableNetwork(NewsApplication.applicationContext())) {
                    topHeadlinesUseCase.runWorkManagerTask(pageNumber)
                    _errorLiveData.value = ""
                } else {
                    if (_errorLiveData.value != NO_INTERNET) {
                        _errorLiveData.value = NO_INTERNET
                        removeLoader.value = true
                    }
                }
            } else {
                removeLoader.value = true
            }
        })
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun getIsLoading(): MutableLiveData<Boolean?>? {
        return mIsLoading
    }

    /*
    Method to transform the find the article from the list
    * @param primary key to find the item
     */
    fun getArticle(autoId: Int): Article? {
        return articleList.find {
            it.autoId == autoId
        }
    }

    /*
    Method to transform the response to adapter list
    @param viewModelScope - scope to launch exection in
    @Param livedaya - get the list result
    @param artical list - current list
    @Param - remove loading value to remove loader
     */
    fun mergeAdapterList() {
        topHeadlinesUseCase.mergeAdapterList(viewModelScope,adapterListLiveData,articleList,removeLoader.value)
    }
    /*
    Method to get the loading state of live data
     */
    fun getLoadingLiveData():LiveData<Boolean?>? {
        return topHeadlinesUseCase.getLoadingLiveData(mIsLoading)
    }

}