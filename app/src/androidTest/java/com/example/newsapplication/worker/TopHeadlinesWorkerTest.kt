package com.example.newsapplication.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TopHeadlinesWorkerTest {
        private lateinit var context: Context

        @Before
        fun setUp() {
            context = ApplicationProvider.getApplicationContext()
        }

        @Test
        fun testHeadlinesWorker() {
            val worker = TestListenableWorkerBuilder<TopHeadlinesWorker>(context).build()
            runBlocking {
                val result = worker.doWork()
                assertThat(result, `is`(ListenableWorker.Result.success()))
            }
        }
    }
