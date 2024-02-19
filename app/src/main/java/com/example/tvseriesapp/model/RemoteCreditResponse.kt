package com.example.tvseriesapp.model

import com.google.gson.annotations.SerializedName

data class RemoteCreditResponse(
    @SerializedName("cast") val cast:List<Cast>
)
