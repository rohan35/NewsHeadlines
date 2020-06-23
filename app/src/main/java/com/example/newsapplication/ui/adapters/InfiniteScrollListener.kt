package com.example.newsapplication.ui.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(var loadMoreListener: OnLoadMoreListener) :
    RecyclerView.OnScrollListener() {

    companion object {
        var VISIBLE_THRESHOLD = 3
    }

    private var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dx == dy)
            return
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount: Int = linearLayoutManager.itemCount
        val lastVisibleItem: Int = linearLayoutManager.findLastVisibleItemPosition()
        if (totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD && totalItemCount != 0) {
            if (loadMoreListener != null) {
                loadMoreListener.onLoadMore(totalItemCount)
            }
        }
    }
    interface OnLoadMoreListener {
        fun onLoadMore(totalItemCount: Int)
    }
}