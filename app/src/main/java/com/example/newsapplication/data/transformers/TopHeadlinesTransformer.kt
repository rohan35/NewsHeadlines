package com.example.newsapplication.data.transformers

import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.utils.PAGE_NUMBER_PARAM_KEY
import com.example.newsapplication.utils.TAG_OUTPUT
import com.example.newsapplication.worker.TopHeadlinesWorker
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.concurrent.TimeUnit

class TopHeadlinesTransformer(
    val topHeadlinesRepo: TopHeadlinesRepo,
    private val workManager: WorkManager
) : TopHeadlinesUseCase {
    override fun getTopHeadlines(): LiveData<List<Article>?>? =
        topHeadlinesRepo.getTopHeadlinesLocally()

    private fun createConstraints() = Constraints.Builder()
        // other values(NOT_REQUIRED, CONNECTED, NOT_ROAMING, METERED)
        .setRequiresBatteryNotLow(true)                 // if the battery is not low
        .setRequiresStorageNotLow(true)                 // if the storage is not low
        .build()

    private fun createWorkRequest(pageNumber: Int): PeriodicWorkRequest {
        val inputData = workDataOf(PAGE_NUMBER_PARAM_KEY to pageNumber)
        return PeriodicWorkRequestBuilder<TopHeadlinesWorker>(
            2,
            TimeUnit.HOURS
        )  // setting period to 2 hours
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
}

