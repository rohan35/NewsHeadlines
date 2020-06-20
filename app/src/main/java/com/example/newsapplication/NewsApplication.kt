package com.example.newsapplication

import android.app.Application

class NewsApplication
    : Application() {

    companion object {
        private var instance: NewsApplication? = null

        fun applicationContext(): NewsApplication {
            return instance as NewsApplication

        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}