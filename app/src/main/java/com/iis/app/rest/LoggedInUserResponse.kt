package com.iis.app.rest

import com.iis.app.data.model.LoggedInUser
import com.google.gson.annotations.SerializedName

data class LoggedInUserResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("user") var loggedInUser: LoggedInUser,
    @SerializedName("token") var token: String,

    )