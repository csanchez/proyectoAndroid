package com.example.iisapp.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("name") var name: String,
    @SerializedName("label") var label: String,
    @SerializedName("value") var value: String,
)


