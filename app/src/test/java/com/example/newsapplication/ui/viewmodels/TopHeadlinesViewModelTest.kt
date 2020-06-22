package com.example.newsapplication.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.data.transformers.TopHeadlinesTransformer
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.Source
import com.example.newsapplication.utility.MainCoroutineRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate


@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(MockitoJUnitRunner::class)
@PrepareForTest(
   TopHeadlinesUseCase::class
)
class TopHeadlinesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()
    private val topHeadlinesUseCase = Mockito.mock(TopHeadlinesUseCase::class.java)
    private lateinit var viewModel: TopHeadlinesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.viewModel = TopHeadlinesViewModel(topHeadlinesUseCase)
    }

    fun getArticleList():List<Article>?
    {
        var articleList = mutableListOf<Article>()
        articleList.add(
            Article(
                1, "rohan", "content", "descriton"
                , "date", Source("2", "NewsTimes"), "title", "url", "urlto"
            )
        )
        return articleList
    }

    fun getTopHeadlinesListLiveData(): LiveData<List<Article>?>? {
        var headlinesLiveData = MutableLiveData<List<Article>?>()
        headlinesLiveData.value = getArticleList()
        return headlinesLiveData
    }

    fun getTopHeadlinesFailureListLiveData(): LiveData<List<Article>?>? {
        var headlinesLiveData = MutableLiveData<List<Article>?>()
        return headlinesLiveData
    }
    @Test
    fun getTopHeadlinesSuccess() {
        Mockito.`when`(topHeadlinesUseCase.getTopHeadlines())
            .thenReturn(getTopHeadlinesListLiveData())
        val observer = Mockito.mock(Observer::class.java) as Observer<List<Article>?>
        val resultLiveData = topHeadlinesUseCase.getTopHeadlines()
        resultLiveData?.observeForever(observer)
        // found check the size
        assertEquals(resultLiveData?.value?.size, 1)
    }

    @Test
    fun getTopHeadlinesFailure() {
        Mockito.`when`(topHeadlinesUseCase.getTopHeadlines())
            .thenReturn(getTopHeadlinesFailureListLiveData())
        val observer = Mockito.mock(Observer::class.java) as Observer<List<Article>?>
        val resultLiveData = topHeadlinesUseCase.getTopHeadlines()
        resultLiveData?.observeForever(observer)
        // not found check the value as null
        assertEquals(resultLiveData?.value, null)
    }

    @Test
    fun getArticleSuccess() {
        viewModel.articleList = getArticleList()?.toMutableList()!!
        val article = viewModel.getArticle(1)
        // found check the descriptionn
        assertEquals(article?.description,viewModel.articleList[0].description)
    }

    @Test
    fun getArticleFailure() {
        viewModel.articleList = getArticleList()?.toMutableList()!!
        val article = viewModel.getArticle(2)
        // not found so will return null
        assertEquals(article,null)
    }


}