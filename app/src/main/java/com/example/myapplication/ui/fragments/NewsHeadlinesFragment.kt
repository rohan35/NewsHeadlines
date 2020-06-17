package com.example.myapplication.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.ui.viewmodels.NewsHeadlinesViewModel

class NewsHeadlinesFragment : Fragment() {

    companion object {
        fun newInstance() =
            NewsHeadlinesFragment()
    }

    private lateinit var viewModel: NewsHeadlinesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsHeadlinesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}