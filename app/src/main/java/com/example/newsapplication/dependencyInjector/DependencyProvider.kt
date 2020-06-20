package com.example.newsapplication.dependencyInjector

import android.content.Context
import androidx.work.WorkManager
import com.example.newsapplication.NewsApplication
import com.example.newsapplication.data.localsource.TopHeadlinesLocalSource
import com.example.newsapplication.data.remotesource.TopHeadlinesRemoteSource
import com.example.newsapplication.data.repositories.AppRepo
import com.example.newsapplication.data.repositories.TopHeadlinesRepo
import com.example.newsapplication.data.transformers.TopHeadlinesTransformer
import com.example.newsapplication.data.usecase.TopHeadlinesUseCase
import com.example.newsapplication.database.TopHeadlinesDao
import com.example.newsapplication.database.TopHeadlinesDatabase
import com.example.newsapplication.factories.TopHeadlinesViewModelFactory
import com.example.newsapplication.interfaces.TopHeadlinesDataSource
import com.example.newsapplication.network.RetrofitService
import com.example.newsapplication.network.ServiceHelper

object DependencyProvider {

    fun getHeadlinesViewModelFactory(): TopHeadlinesViewModelFactory {
        return TopHeadlinesViewModelFactory()
    }

    /**
     * Get the object of Top Headelines use case
     */
    private fun getTopHeadlinesUseCase(context: Context): TopHeadlinesUseCase {
        return TopHeadlinesTransformer(getTopHeadlinesRepo(), getWorkManager())
    }

    /**
     *get the object of top headlines repositriry
     */
     fun getTopHeadlinesRepo(): TopHeadlinesRepo {
        return TopHeadlinesRepo(getTopHeadlinesLocalDataSource(), getRemoteDataSource())
    }

    /**
     * Get the work manager instance
     */
    private fun getWorkManager(): WorkManager {
        return WorkManager.getInstance(NewsApplication.applicationContext())
    }

    /**
     * Get the pbject of local data source
     */
    private fun getTopHeadlinesLocalDataSource(): TopHeadlinesDataSource {
        return TopHeadlinesLocalSource(geTopHeadlinesDao())
    }

    /**
     * Get Dao Top headlines
     */
    private fun geTopHeadlinesDao(): TopHeadlinesDao {
        return TopHeadlinesDatabase.getDatabase(NewsApplication.applicationContext())
            .topHeadlinesDao()
    }

    /**
     * Get Remote data dource Top headlines
     */
    private fun getRemoteDataSource(): TopHeadlinesDataSource {
        return TopHeadlinesRemoteSource(getAppRepository())
    }

    /**
     * Get App repo object
     */
    private fun getAppRepository(): AppRepo {
        return AppRepo(getServiceHelper())
    }

    /**
     * Get Service helper
     */
    private fun getServiceHelper(): ServiceHelper {
        return ServiceHelper(RetrofitService.networkService)
    }

}