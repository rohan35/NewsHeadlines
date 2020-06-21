package com.example.newsapplication.data.localsource

import androidx.lifecycle.LiveData
import com.example.newsapplication.database.TopHeadlinesDao
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines

class TopHeadlinesLocalSource(private var topHeadlinesDao: TopHeadlinesDao) :
    TopHeadlinesDataSource {
    override suspend fun getTopHeadlines(pageNumber:Int): Any? {
        // nothing will be implemented
        return null
    }

    override fun getTopHeadlinesLocally(): LiveData<List<Article>?> = topHeadlinesDao.getTopHeadlines()

    override suspend fun insertTopHeadlinesLocally(article: Article) {
        topHeadlinesDao.insert(article)
    }

    override suspend fun deleteTopHeadlines() {
        topHeadlinesDao.deleteAll()
    }

}