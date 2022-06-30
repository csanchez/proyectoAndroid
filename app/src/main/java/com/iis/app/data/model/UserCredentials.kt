package com.iis.app.data.model

data class UserCredentials(
    val rfc: String,
    val password: String,
    val deviceId: String,
    val deviceName: String,
    val fcmToken: String
)