package com.example.newsapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines


@Dao
interface TopHeadlinesDao {
    /**
     * Select all headlines from the topHeadline table.
     *
     * @return all data.
     */
    @Query("SELECT * from article")
    fun getTopHeadlines(): LiveData<List<Article>?>

    /**
     * Insert a topHeadline in the database. If the topHeadline already exists, replace it.
     *
     * @param topHeadline the headline to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topHeadlines: Article)

    /**
     * Delete all headlines.
     */

    @Query("DELETE FROM article")
    suspend fun deleteAll()

    /**
     * update result every time for total quantity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTotalResults(topHeadlines: TopHeadlines)

    /**
     * get total quantity
     */
    @Query("SELECT totalResults from topHeadlines")
    fun getTotalResults(): LiveData<Int>
}