package com.example.newsapplication.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.ui.viewmodels.TopHeadlinesViewModel

class TopHeadlinesViewModelFactory(private var topHeadlinesUseCase: TopHeadlinesUseCase) : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopHeadlinesViewModel(topHeadlinesUseCase) as T
    }
}