package com.example.newsapplication.dependencyInjector

import com.example.newsapplication.factories.TopHeadlinesViewModelFactory

object DependencyProvider {

    fun getHeadlinesViewModelFactory(): TopHeadlinesViewModelFactory {
        return TopHeadlinesViewModelFactory()
    }

}