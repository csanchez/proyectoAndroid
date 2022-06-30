package com.iis.app.ui.notifications

import com.iis.app.data.model.IisNotification

data class NotificationResult(
    val success: IisNotification? = null,
    val error: String?=null
)
