package com.example.newsapplication.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.dependencyInjector.DependencyProvider
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.network.NetworkUtils
import com.example.newsapplication.utils.PAGE_NUMBER_PARAM_KEY
import com.example.newsapplication.utils.PAGE_SIZE_PARAM_KEY
import com.example.newsapplication.utils.TOTAL_RESULTS
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception

class TopHeadlinesWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private var topHeadlinesRepo: TopHeadlinesRepo = DependencyProvider.getTopHeadlinesRepo();
    override suspend fun doWork(): Result = coroutineScope {
        try {
            var pageNumber:Int = 1
            val response = async {
                pageNumber = inputData.getInt(PAGE_NUMBER_PARAM_KEY,1)
                topHeadlinesRepo.getTopHeadlines(pageNumber)
            }.await()
            if (response != null) {
                if (pageNumber == 1) { // delete all items
                    topHeadlinesRepo.deleteTopHeadlines()
                }

                val topHeadlinesObject = NetworkUtils.getModelFromJsonString(
                    (Gson().toJsonTree
                        (response)).asJsonObject.toString(),
                    TopHeadlines::class.java
                )!!
                topHeadlinesRepo.updateTotalResults(topHeadlinesObject)
                // adding assertion as null check is already added
                topHeadlinesObject.articles?.forEach {
                    topHeadlinesRepo.insertTopHeadlines(it)
                }
                return@coroutineScope Result.success()
            }
            Result.failure()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}