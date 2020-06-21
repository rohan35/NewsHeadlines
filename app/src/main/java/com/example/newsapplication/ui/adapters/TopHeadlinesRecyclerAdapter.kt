package com.example.newsapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.BR
import com.example.newsapplication.databinding.TopHeadlinesItemBinding
import com.example.newsapplication.model.Article

class TopHeadlinesRecyclerAdapter(var articleList: List<Article>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onPageFinished: ((pageNumber:Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HeadlinesViewHolder(
            TopHeadlinesItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HeadlinesViewHolder).bind(articleList[position] as Article)
    }

    inner class HeadlinesViewHolder(private var mBinding: TopHeadlinesItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(data: Article) {
            mBinding.setVariable(BR.topHeadlinesArticle, data)
        }
    }
    fun setData(articleList: List<Article>)
    {
        this.articleList = articleList
        notifyDataSetChanged()
    }
}