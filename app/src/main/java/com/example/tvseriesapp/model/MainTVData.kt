package com.example.tvseriesapp.model

data class MainTVData(
    val page:Int,
    val total_results:Int,
    val total_pages:Int,
    val results:List<TVData>
)
