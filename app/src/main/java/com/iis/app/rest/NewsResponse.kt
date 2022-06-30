package com.iis.app.rest


import com.iis.app.data.model.New
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("news") var news: List<New>
)
