package com.example.iisapp.ui.notifications

import com.example.iisapp.data.model.IisNotification

data class NotificationResult(
    val success: IisNotification? = null,
    val error: String?=null
)
