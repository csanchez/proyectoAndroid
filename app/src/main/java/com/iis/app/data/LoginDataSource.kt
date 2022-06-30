package com.iis.app.data

import com.iis.app.data.model.UserCredentials
import com.iis.app.rest.ApiClient


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

     suspend fun login(userCredentials: UserCredentials): Result<Any> {// LoggedInUser?  { //Result<LoggedInUser>?
         //try {
            // TODO: handle loggedInUser authentication
           // val userCredentials = UserCredentials(username,password,deviceId,deviceName,fcmToken)

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