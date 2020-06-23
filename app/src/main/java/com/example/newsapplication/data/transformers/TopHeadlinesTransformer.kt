package com.example.newsapplication.data.transformers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.ComponentViewType
import com.example.newsapplication.model.LoaderItemModel
import com.example.newsapplication.utils.PAGE_NUMBER_PARAM_KEY
import com.example.newsapplication.utils.TAG_OUTPUT
import com.example.newsapplication.utils.WORK_MANAGER_TAG
import com.example.newsapplication.worker.TopHeadlinesWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TopHeadlinesTransformer(
    val topHeadlinesRepo: TopHeadlinesRepo,
    private val workManager: WorkManager
) : TopHeadlinesUseCase {
    override fun getTopHeadlines(): LiveData<List<Article>?>? =
        topHeadlinesRepo.getTopHeadlinesLocally()


    private fun createConstraints() = Constraints.Builder()
        // other values(NOT_REQUIRED, CONNECTED, NOT_ROAMING, METERED)
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)                 // if the battery is not low
        .setRequiresStorageNotLow(true)                 // if the storage is not low
        .build()

    private fun createWorkRequest(pageNumber: Int): PeriodicWorkRequest {
        val inputData = workDataOf(PAGE_NUMBER_PARAM_KEY to pageNumber)
        return PeriodicWorkRequestBuilder<TopHeadlinesWorker>(
            2,
            TimeUnit.HOURS
        )
            .addTag(WORK_MANAGER_TAG)
            // setting period to 2 hours
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 2, TimeUnit.HOURS)
            .setConstraints(createConstraints())
            .setInputData(inputData)
            .build()
    }

    override fun runWorkManagerTask(pageNumber: Int) {
        workManager.enqueueUniquePeriodicWork(
            TAG_OUTPUT,
            ExistingPeriodicWorkPolicy.REPLACE,
            createWorkRequest(pageNumber)
        )
    }

    override fun getTotalResults(): LiveData<Int>? = topHeadlinesRepo.getTotalResults()
    override fun getLoadingLiveData(liveData: MutableLiveData<Boolean?>): LiveData<Boolean?>? = topHeadlinesRepo.getLoadingLiveData(liveData)
    override fun mergeAdapterList(
        scope: CoroutineScope,
        articleListLiveData: MutableLiveData<ArrayList<ComponentViewType>>,
        articleList: MutableList<Article>,
        removeLoader: Boolean?
    ) {
        // launching in viewmodel scope
        scope.launch {
            var headlineList = ArrayList<ComponentViewType>()
            articleList.forEach {
                headlineList.add(it)
            }
            if (removeLoader == false)
                headlineList.add(LoaderItemModel(true))
            articleListLiveData.postValue(headlineList)
        }
    }

}

