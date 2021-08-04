package com.example.iisapp.rest

import com.example.iisapp.data.model.LoggedInUser
import com.google.gson.annotations.SerializedName

data class LoggedInUserResponse(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: String,
    @SerializedName("user") var loggedInUser: LoggedInUser,

    )