package com.example.newsapplication.data.transformers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
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
import java.util.concurrent.CountDownLatch

@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(MockitoJUnitRunner::class)
@PrepareForTest(
    TopHeadlinesRepo::class,
    WorkManager::class
)
class TopHeadlinesTransformerTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()
    private val topHeadlinesRepo = Mockito.mock(TopHeadlinesRepo::class.java)
    private val workManager = Mockito.mock(WorkManager::class.java)
    private lateinit var headlinesTransformer: TopHeadlinesTransformer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.headlinesTransformer = TopHeadlinesTransformer(topHeadlinesRepo, workManager)
    }

    fun getTopHeadlinesListLiveData(): LiveData<List<Article>?>? {
        var headlinesLiveData = MutableLiveData<List<Article>?>()
        var articleList = mutableListOf<Article>()
        articleList.add(
            Article(
                1, "rohan", "content", "descriton"
                , "date", Source("2", "NewsTimes"), "title", "url", "urlto"
            )
        )
        headlinesLiveData.value = articleList
        return headlinesLiveData
    }

    fun getTopHeadlinesFailureListLiveData(): LiveData<List<Article>?>? {
        var headlinesLiveData = MutableLiveData<List<Article>?>()
        return headlinesLiveData
    }

    @Test
    fun getTopHeadlinesSuccess() {
        Mockito.`when`(topHeadlinesRepo.getTopHeadlinesLocally())
            .thenReturn(getTopHeadlinesListLiveData())
        val observer = Mockito.mock(Observer::class.java) as Observer<List<Article>?>
        val resultLiveData = headlinesTransformer.getTopHeadlines()
        resultLiveData?.observeForever(observer)
        assertEquals(resultLiveData?.value?.size, 1)
    }

    @Test
    fun getTopHeadlinesFailure() {
        Mockito.`when`(topHeadlinesRepo.getTopHeadlinesLocally())
            .thenReturn(getTopHeadlinesFailureListLiveData())
        val observer = Mockito.mock(Observer::class.java) as Observer<List<Article>?>
        val resultLiveData = headlinesTransformer.getTopHeadlines()
        resultLiveData?.observeForever(observer)
        assertEquals(resultLiveData?.value, null)
    }
}