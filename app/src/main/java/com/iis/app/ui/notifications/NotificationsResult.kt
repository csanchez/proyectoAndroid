package com.iis.app.ui.notifications

import com.iis.app.data.model.IisNotification


data class NotificationsResult(
    val success: List<IisNotification>? = null,
    val error: String?=null
)
