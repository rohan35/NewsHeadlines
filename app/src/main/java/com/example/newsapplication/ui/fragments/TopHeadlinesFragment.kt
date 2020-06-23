package com.example.newsapplication.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.BR
import com.example.newsapplication.R
import com.example.newsapplication.databinding.TopHeadlinesFragmentBinding
import com.example.newsapplication.dependencyInjector.DependencyProvider
import com.example.newsapplication.ui.adapters.InfiniteScrollListener
import com.example.newsapplication.ui.adapters.TopHeadlinesRecyclerAdapter
import com.example.newsapplication.ui.viewmodels.TopHeadlinesViewModel


class TopHeadlinesFragment : Fragment(), InfiniteScrollListener.OnLoadMoreListener {
    private var mBinding: TopHeadlinesFragmentBinding? = null
    private var mAdapter: TopHeadlinesRecyclerAdapter? = null
    private lateinit var infiniteScrollListener: InfiniteScrollListener

    // reset recycler view position back state variable
    var state: Parcelable? = null

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
        mBinding?.setVariable(BR.viewModel, getViewModel())
//        getViewModel().setIsLoading(true)
        infiniteScrollListener = InfiniteScrollListener(this)
        // initailise recycler view
        setUpRecyclerView()
        // get errors
        processErrorLiveData()
        // process article list
        processArticleList()
        // remove the loading bar from the list
        removeLoaderFromAdapterList()
        // observer loading state
        observeLoadingState()
        // observe adapterlist
        observerAdapterList()
    }

    override fun onPause() {
        super.onPause()
        state = mBinding?.rvTopHeadlines?.layoutManager?.onSaveInstanceState()
    }

    private fun setUpRecyclerView() {
        mAdapter =
            TopHeadlinesRecyclerAdapter(ArrayList())
        mBinding?.rvTopHeadlines?.adapter = mAdapter
        mBinding?.rvTopHeadlines?.addOnScrollListener(infiniteScrollListener)
        mAdapter!!.onItemClick = {
            val fragment = TopHeadlinesDetailsFragment.newInstance(it)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, fragment)
                ?.addToBackStack(TopHeadlinesDetailsFragment.TAG)
                ?.commit()
        }
    }

    /*
Method to remove loading bar from the list
    */
    private fun removeLoaderFromAdapterList() {
        getViewModel().removeLoader.observe(viewLifecycleOwner, Observer { loader ->
            if (loader != null && loader) {
                getViewModel().mergeAdapterList()
            }
        })
    }

    /*
        Method to observe any errors if occured
         */
    private fun processErrorLiveData() {
        getViewModel().errorLiveData.observe(viewLifecycleOwner, Observer { response ->
            response?.let { errorResponse ->
                // show error
                if (!errorResponse.isNullOrBlank()) {
                    showDialog(errorResponse)
                    getViewModel().setIsLoading(false)
                }
            }
        })
    }

    /*
    Methosd to observe teh database results and show to UI
     */
    private fun processArticleList() {
        getViewModel().getTopHeadlines()?.observe(viewLifecycleOwner, Observer { response ->
            if (!response.isNullOrEmpty()) {
                getViewModel().articleList = response.toMutableList()
                getViewModel().mergeAdapterList()
            } else {
                // make an online request
                getViewModel().runWorkManager(0)
            }
        })
    }

    /*
    Method to check the current loading state of loader
     */
    private fun observeLoadingState() {
        getViewModel().getLoadingLiveData()?.observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {
                if (state) {
                    getViewModel().setIsLoading(state)
                }
            }
        })
    }

    /*
    Method to observe adapter list and show the values to the UI if found
     */
    private fun observerAdapterList() {
        getViewModel().adapterListLiveData.observe(viewLifecycleOwner, Observer { list ->
            if (!list.isNullOrEmpty()) {
                // set the data
                mAdapter?.setData(list)
                // reset recycler view position back
                if (state != null) {
                    mBinding?.rvTopHeadlines?.layoutManager?.onRestoreInstanceState(state)
                    state = null
                }

                getViewModel().setIsLoading(false)
            }
        })
    }

    fun showDialog(msg: String) {
        this.context?.let { context ->
            val builder =
                AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.app_name))
            builder.setMessage(msg)
            builder.setCancelable(false)
            builder.setPositiveButton(
                context.getString(R.string.ok)
            ) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    /**
     * getViewModel() provides the object of viewModel with the help [DependencyProvider]
     */
    private fun getViewModel(): TopHeadlinesViewModel {
        return ViewModelProvider(activity!!, DependencyProvider.getHeadlinesViewModelFactory()).get(
            TopHeadlinesViewModel::class.java
        )
    }

    override fun onLoadMore(totalItemCount: Int) {
        getViewModel().runWorkManager(totalItemCount - 1)
    }
}