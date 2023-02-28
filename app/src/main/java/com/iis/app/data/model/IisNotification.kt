package com.iis.app.data.model

import com.google.gson.annotations.SerializedName

data class IisNotification (
    @SerializedName("id") var id: Int,
    @SerializedName("userNotificationId") var userNotificationId: Int,
    @SerializedName("title") var title: String,
    @SerializedName("message") var message: String,
    @SerializedName("url") var url: String,
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("status") var status: String,

    @SerializedName("notificationType") var notificationType: String,
    @SerializedName("originType") var originType: String,
    @SerializedName("originName") var originName: String,
    @SerializedName("originInitials") var originInitials: String,
    @SerializedName("sender") var sender: String,
    @SerializedName("color") var color: String



)