package com.example.newsapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.TopHeadlines
import com.example.newsapplication.utils.RoomTypeConverters

/**
 * The Room Database that contains the top headlines table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [TopHeadlines::class,Article::class],version = 2,exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class TopHeadlinesDatabase :RoomDatabase(){
    abstract fun topHeadlinesDao():TopHeadlinesDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TopHeadlinesDatabase? = null

        fun getDatabase(context: Context): TopHeadlinesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TopHeadlinesDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}