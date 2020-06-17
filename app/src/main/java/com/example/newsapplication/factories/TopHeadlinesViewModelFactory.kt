package com.example.newsapplication.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.ui.viewmodels.TopHeadlinesViewModel

class TopHeadlinesViewModelFactory: ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopHeadlinesViewModel() as T
    }
}