package com.example.newsapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapplication.R
import com.example.newsapplication.ui.fragments.TopHeadlinesFragment

class TopHeadlinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TopHeadlinesFragment.newInstance())
                    .commitNow()
        }
    }
}