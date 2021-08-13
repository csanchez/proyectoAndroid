package com.example.iisapp.rest


import com.google.gson.annotations.SerializedName


data class DeviceRegisteredResponse(
    @SerializedName("message") var message: String,
)
