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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.example.newsapplication.BR
import com.example.newsapplication.R
import com.example.newsapplication.databinding.TopHeadlinesFragmentBinding
import com.example.newsapplication.dependencyInjector.DependencyProvider
import com.example.newsapplication.ui.adapters.TopHeadlinesRecyclerAdapter
import com.example.newsapplication.ui.viewmodels.TopHeadlinesViewModel
import com.example.newsapplication.utils.TAG_OUTPUT
import com.example.newsapplication.utils.TOTAL_RESULTS
import com.example.newsapplication.utils.WORK_MANAGER_TAG

class TopHeadlinesFragment : Fragment() {
    private var mBinding: TopHeadlinesFragmentBinding? = null
    private var mAdapter:TopHeadlinesRecyclerAdapter? = null
    private lateinit var scrollListener: RecyclerView.OnScrollListener
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
        mBinding?.setLifecycleOwner(viewLifecycleOwner)
        mBinding?.setVariable(BR.viewModel,getViewModel())
        setUpRecyclerView()
        processErrorLiveData()
        processArticleList()
        processAdapterPadding()
    }

    private fun setUpRecyclerView()
    {
        mAdapter =
            TopHeadlinesRecyclerAdapter(getViewModel().articleList)
        mBinding?.rvTopHeadlines?.adapter = mAdapter
        setRecyclerViewScrollListener()
        mAdapter!!.onItemClick = {
            val fragment =  TopHeadlinesDetailsFragment.newInstance(it)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, fragment)
                ?.addToBackStack(TopHeadlinesDetailsFragment.TAG)
                ?.commit()
        }
    }

    private fun processAdapterPadding()
    {
        getViewModel().adapterPadding.observe(viewLifecycleOwner, Observer {
            showPadding->
            if(showPadding)
            {
                val padding = resources.getDimensionPixelOffset(R.dimen.dp_50)
                mBinding?.rvTopHeadlines?.setPadding(0,0,0,padding)
            }
            else{
                mBinding?.rvTopHeadlines?.setPadding(0,0,0,0)
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

    private fun processArticleList() {
        getViewModel().getTopHeadlines()?.observe(viewLifecycleOwner, Observer { response ->
                if (!response.isNullOrEmpty()) {
                    getViewModel().articleList = response.toMutableList()
                    mAdapter?.setData(getViewModel().articleList)
                    getViewModel().setIsLoading(false)
            } else {
                // make an online request
                getViewModel().runWorkManager(0)
            }
        })
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = linearLayoutManager.itemCount
                if (totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    getViewModel().runWorkManager(totalItemCount)
                }
            }
        }
        mBinding?.rvTopHeadlines?.addOnScrollListener(scrollListener)
    }

    /**
     * getViewModel() provides the object of viewModel with the help [DependencyProvider]
     */
    private fun getViewModel(): TopHeadlinesViewModel {
        return ViewModelProvider(activity!!, DependencyProvider.getHeadlinesViewModelFactory()).get(
            TopHeadlinesViewModel::class.java
        )
    }
}