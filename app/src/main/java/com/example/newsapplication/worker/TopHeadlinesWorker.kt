package com.example.newsapplication.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.dependencyInjector.DependencyProvider
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
            val response = async {
                topHeadlinesRepo.getTopHeadlines()
            }.await()
            if (response != null)
                Result.success()
            Result.failure()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}