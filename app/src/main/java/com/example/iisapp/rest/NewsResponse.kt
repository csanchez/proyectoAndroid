package com.example.iisapp.rest


import com.example.iisapp.data.model.New
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("news") var news: List<New>
)
