package com.example.iisapp.ui.notifications

import com.example.iisapp.data.model.IisNotification


data class NotificationsResult(
    val success: List<IisNotification>? = null,
    val error: String?=null
)
