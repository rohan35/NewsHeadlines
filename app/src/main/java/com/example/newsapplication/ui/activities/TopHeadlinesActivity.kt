package com.example.newsapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.newsapplication.R
import com.example.newsapplication.ui.fragments.TopHeadlinesFragment
import kotlinx.android.synthetic.main.top_headlines_activity.view.*

class TopHeadlinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.top_headlines_activity)
        setSupportActionBar(binding.root.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TopHeadlinesFragment.newInstance())
                    .commit()
        }
    }
}