package com.example.newsapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.BR
import com.example.newsapplication.databinding.LoadingViewBinding
import com.example.newsapplication.databinding.TopHeadlinesItemBinding
import com.example.newsapplication.model.Article
import com.example.newsapplication.model.ComponentViewType

class TopHeadlinesRecyclerAdapter(var adapterList: ArrayList<ComponentViewType>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var oldSize:Int = 0
    var onItemClick: ((id: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ComponentViewType.VIEW_TYPE_LIST -> {
                return HeadlinesViewHolder(
                    TopHeadlinesItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )

            }
            else -> {
                return ProgressViewHolder(
                    LoadingViewBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return adapterList[position].baseType
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ComponentViewType.VIEW_TYPE_LIST)
            (holder as HeadlinesViewHolder).bind(adapterList[position] as Article)
        else {
            (holder as ProgressViewHolder).progressBar.isIndeterminate = true;
        }
    }

    inner class HeadlinesViewHolder(private var mBinding: TopHeadlinesItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        init {
            mBinding.cardView.setOnClickListener {
                onItemClick?.invoke((adapterList[adapterPosition] as Article).autoId?:0)
            }
        }

        fun bind(data: Article) {
            mBinding.setVariable(BR.topHeadlinesArticle, data)
        }
    }

    inner class ProgressViewHolder(private var mBinding: LoadingViewBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        var progressBar = mBinding.progressbar
    }

    fun setData(adapterList: ArrayList<ComponentViewType>) {
        this.adapterList = adapterList
        if(adapterList.size>oldSize)
        {
            notifyItemRangeChanged(oldSize,adapterList.size - oldSize)
            oldSize = adapterList.size
        }
        else
        {
           notifyDataSetChanged()
        }

    }
}