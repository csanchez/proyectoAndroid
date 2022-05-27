package com.example.iisapp.rest


import com.example.iisapp.data.model.IisNotification
import com.google.gson.annotations.SerializedName

data class NotificationsResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("notifications") var notifications: List<IisNotification>
)