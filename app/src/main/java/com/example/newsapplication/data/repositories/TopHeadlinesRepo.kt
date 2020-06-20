package com.example.newsapplication.data.repositories

import com.example.newsapplication.data.localsource.TopHeadlinesLocalSource
import com.example.newsapplication.data.remotesource.TopHeadlinesRemoteSource
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.TopHeadlines

class TopHeadlinesRepo(private val topHeadlinesLocalSource: TopHeadlinesDataSource,
                       private val topHeadlinesRemoteSource: TopHeadlinesDataSource) {
  /**
   * get top headlines remotely
   */
  suspend fun getTopHeadlines() = topHeadlinesRemoteSource.getTopHeadlines()
  /**
   * get top headlines locally
   */
  suspend fun getTopHeadlinesLocally() = topHeadlinesLocalSource.getTopHeadlinesLocally()

    /**
     * Insert items into database
      */
   suspend fun insertTopHeadlines(topHeadlines: TopHeadlines)
    {
        topHeadlinesLocalSource.insertTopHeadlinesLocally(topHeadlines)
    }
   suspend fun deleteTopHeadlines()
   {
       topHeadlinesLocalSource
   }

}