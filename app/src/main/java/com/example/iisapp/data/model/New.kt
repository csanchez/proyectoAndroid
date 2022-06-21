package com.example.iisapp.data.model

import com.google.gson.annotations.SerializedName

data class New(
    @SerializedName("Id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("url") var url: String,
    @SerializedName("image_url") var imageUrl: String,
    @SerializedName("color") var color: String,
    @SerializedName("num") var num: Int


    )
