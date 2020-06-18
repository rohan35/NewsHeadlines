package com.example.newsapplication.data.repositories

import com.example.newsapplication.data.localsource.TopHeadlinesLocalSource
import com.example.newsapplication.data.remotesource.TopHeadlinesRemoteSource

class TopHeadlinesRepo(val localSource: TopHeadlinesLocalSource,val topHeadlinesRemoteSource: TopHeadlinesRemoteSource) {
  suspend fun getTopHeadlines() = topHeadlinesRemoteSource.getTopHeadlines()
}