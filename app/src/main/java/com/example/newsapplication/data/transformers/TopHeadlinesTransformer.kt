package com.example.newsapplication.data.transformers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.work.*
import com.example.newsapplication.NewsApplication
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.network.NetworkResource
import com.example.newsapplication.network.NetworkUtils
import com.example.newsapplication.utils.TAG_OUTPUT
import com.example.newsapplication.worker.TopHeadlinesWorker
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.concurrent.TimeUnit

class TopHeadlinesTransformer(
    val topHeadlinesRepo: TopHeadlinesRepo,
    private val workManager: WorkManager
) : TopHeadlinesUseCase {
    override fun getTopHeadlines(): LiveData<NetworkResource<TopHeadlines?>> =
        liveData(Dispatchers.IO) {
            try {
                if (!NetworkUtils.verifyAvailableNetwork(NewsApplication.applicationContext())) {
                    emit(
                        NetworkResource.success(
                            data = topHeadlinesRepo.getTopHeadlinesLocally()
                        )
                    )
                    return@liveData
                } else {
                    runWorkManagerTask()
                }

            } catch (exception: Exception) {
                emit(
                    NetworkResource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }

    private fun createConstraints() = Constraints.Builder()
        // other values(NOT_REQUIRED, CONNECTED, NOT_ROAMING, METERED)
        .setRequiresBatteryNotLow(true)                 // if the battery is not low
        .setRequiresStorageNotLow(true)                 // if the storage is not low
        .build()

    private fun createWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<TopHeadlinesWorker>(
            2,
            TimeUnit.HOURS
        )  // setting period to 2 hours
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 2, TimeUnit.HOURS)
            .setConstraints(createConstraints())
            .build()
    }

    private fun runWorkManagerTask() {
        workManager.enqueueUniquePeriodicWork(
            TAG_OUTPUT,
            ExistingPeriodicWorkPolicy.REPLACE,
            createWorkRequest()
        )
    }
}

