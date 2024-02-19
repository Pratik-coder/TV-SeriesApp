package com.example.tvseriesapp.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager?):RecyclerView.OnScrollListener() {

   abstract fun isLastPage():Boolean
   abstract fun isLoading():Boolean
   abstract fun loadMoreItems()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (layoutManager==null){
            return
        }
        val visibleItemCount=layoutManager!!.childCount
        val totalItemCount=layoutManager!!.itemCount
        val firstVisibleItemPosition=layoutManager!!.findFirstVisibleItemPosition()

        if (!isLoading()&& !isLastPage()){
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0){
               loadMoreItems()
            }
        }
    }
}