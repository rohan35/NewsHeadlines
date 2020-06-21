package com.example.newsapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines


@Dao
interface TopHeadlinesDao {
    /**
     * Select all headlines from the topHeadline table.
     *
     * @return all data.
     */
    @Query("SELECT * from topHeadlines")
     fun getTopHeadlines(): LiveData<List<Article>?>
    /**
     * Insert a topHeadline in the database. If the topHeadline already exists, replace it.
     *
     * @param topHeadline the headline to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(topHeadlines: Article)

    /**
     * Delete all headlines.
     */

    @Query("DELETE FROM topHeadlines")
    suspend fun deleteAll()
}