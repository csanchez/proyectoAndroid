package com.iis.app.ui.login

import com.iis.app.data.model.LoggedInUser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    //val success: LoggedInUserView? = null,
    val success: LoggedInUser? = null,
    val error: String?=null
    //val error: Int? = null
)