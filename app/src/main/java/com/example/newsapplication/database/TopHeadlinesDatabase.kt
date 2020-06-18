package com.example.newsapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapplication.model.TopHeadlines

/**
 * The Room Database that contains the top headlines table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [TopHeadlines::class],version = 1,exportSchema = false)
abstract class TopHeadlinesDatabase :RoomDatabase(){
    abstract fun topHeadlinesDao():TopHeadlinesDao
}