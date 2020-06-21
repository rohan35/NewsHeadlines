package com.example.newsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.BR
import com.example.newsapplication.R
import com.example.newsapplication.databinding.TopHeadlinesDetailLayoutBinding
import com.example.newsapplication.dependencyInjector.DependencyProvider
import com.example.newsapplication.ui.viewmodels.TopHeadlinesViewModel

class TopHeadlinesDetailsFragment : Fragment() {
    private var mBinding: TopHeadlinesDetailLayoutBinding? = null

    companion object {
        val TAG = "detailFragment"
        val ID = "id"
        fun newInstance(autoId: Int) = TopHeadlinesDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ID, autoId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // init data binding
        mBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.top_headlines_detail_layout,
                container,
                false
            )
        // adding assertion as we have created object above and we are sure it will not be null
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding?.setLifecycleOwner(viewLifecycleOwner)
        mBinding?.setVariable(BR.viewModel, getViewModel())
        getViewModel().setIsLoading(true)
        mBinding?.setVariable(
            BR.topHeadlinesArticle,
            getViewModel().getArticle(arguments?.getInt(ID) ?: 0)
        )
        getViewModel().setIsLoading(false)

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