package com.example.newsapplication.data.usecase

import androidx.lifecycle.LiveData
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.network.NetworkResource

interface TopHeadlinesUseCase {
    fun getTopHeadlines(): LiveData<NetworkResource<TopHeadlines?>>
}