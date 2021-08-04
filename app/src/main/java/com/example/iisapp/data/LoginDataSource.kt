package com.example.iisapp.data

import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.UserCredentials
import java.io.IOException
import com.example.iisapp.rest.ApiClient
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

     fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val userCredentials = UserCredentials(username,password)

            GlobalScope.launch (Dispatchers.Main) { ApiClient.login(userCredentials) }

            val fakeUser = LoggedInUser("email.com", "Jane Doe","sadd123456","carlos","sacnhez","none","www.picture","adsadas")



            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}