package com.example.iisapp.data

import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.UserCredentials


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(userCredentials: UserCredentials):  Result<Any> { // Result<LoggedInUser>? {
        // handle login

        val result = dataSource.login(userCredentials)

        if (result is Result.Success) {
            result.data?.let { setLoggedInUser(it as LoggedInUser ) }
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}