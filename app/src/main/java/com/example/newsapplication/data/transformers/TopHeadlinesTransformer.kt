package com.example.newsapplication.data.transformers

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.network.NetworkResource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class TopHeadlinesTransformer(val topHeadlinesRepo: TopHeadlinesRepo) : TopHeadlinesUseCase {
    override fun getTopHeadlines(): LiveData<NetworkResource<TopHeadlines?>> =
        liveData(Dispatchers.IO) {
            try {

             } catch (exception: Exception) {
                emit(
                    NetworkResource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }
}