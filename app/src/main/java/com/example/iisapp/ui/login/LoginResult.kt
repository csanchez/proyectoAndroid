package com.example.iisapp.ui.login

import com.example.iisapp.data.model.LoggedInUser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    //val success: LoggedInUserView? = null,
    val success: LoggedInUser? = null,
    val error: String?=null
    //val error: Int? = null
)