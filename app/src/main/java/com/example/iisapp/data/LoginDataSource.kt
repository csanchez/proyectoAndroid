package com.example.iisapp.data

import android.util.Log
import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.UserCredentials
import com.example.iisapp.exceptions.LoginException
import com.example.iisapp.exceptions.NetworkException
import java.io.IOException
import com.example.iisapp.rest.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

     suspend fun login(username: String, password: String, deviceId: String, deviceName: String): Result<Any> {// LoggedInUser?  { //Result<LoggedInUser>?
         //try {
            // TODO: handle loggedInUser authentication
            val userCredentials = UserCredentials(username,password,deviceId,deviceName)

            //var result : LoggedInUser

            //GlobalScope.launch(Dispatchers.Main) {
                return ApiClient.login(userCredentials)
            //}

            //GlobalScope.launch(Dispatchers.Main) { ApiClient.login(userCredentials) }
           //val result2 = LoggedInUser("email.com", "Jane Doe","sadd123456","carlos","sacnhez","none","www.picture","adsadas")

            //Result.Success(result)

       /* }
        catch (nE: NetworkException){
            throw nE
        }
        /*catch (le: LoginException){
            throw le
        }*/
        catch (e: Throwable) {
            throw IOException("Error logging in", e)
        }*/
    }

    fun logout() {
        // TODO: revoke authentication
    }
}