package com.example.newsapplication.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import com.example.newsapplication.R
import com.example.newsapplication.databinding.TopHeadlinesFragmentBinding
import com.example.newsapplication.dependencyInjector.DependencyProvider
import com.example.newsapplication.ui.viewmodels.TopHeadlinesViewModel
import com.example.newsapplication.utils.TAG_OUTPUT

class TopHeadlinesFragment : Fragment() {
    private var mBinding: TopHeadlinesFragmentBinding? = null

    companion object {
        fun newInstance() =
            TopHeadlinesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // init data binding
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.top_headlines_fragment, container, false)
        // adding assertion as we have created object above and we are sure it will not be null
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getViewModel().getTopHeadlines()
        processWorkManagerResponse()
        processErrorLiveData()
    }

    private fun processWorkManagerResponse() {
        DependencyProvider.getWorkManager()
            // requestId is the WorkRequest id
            .getWorkInfosByTagLiveData(TAG_OUTPUT)
            .observe(viewLifecycleOwner, Observer {
                if (!it.isNullOrEmpty()) {
                    val workInfo = it[0]
                    workInfo?.let {

                    }
                }
            })
    }

    private fun processErrorLiveData() {
        getViewModel().errorLiveData.observe(viewLifecycleOwner, Observer { response ->
            response?.let { errorResponse ->
                // show error
            }
        })
    }

    /**
     * getViewModel() provides the object of viewModel with the help [DependencyProvider]
     */
    private fun getViewModel(): TopHeadlinesViewModel {
        return ViewModelProvider(this, DependencyProvider.getHeadlinesViewModelFactory()).get(
            TopHeadlinesViewModel::class.java
        )
    }
}