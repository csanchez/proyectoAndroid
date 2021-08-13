package com.example.iisapp.rest


import com.example.iisapp.data.model.UserCredentials
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log
import com.example.iisapp.exceptions.LoginException

import com.example.iisapp.exceptions.NetworkException

import org.json.JSONObject
import java.net.SocketTimeoutException

import com.example.iisapp.data.Result
import com.example.iisapp.data.ISSUtils
import com.example.iisapp.data.model.Device

class ApiClient {


    companion object {
        //private const val baseUrl = "https://iis-notificaciones-api.herokuapp.com/api/"
        private const val baseUrl = "https://notificaciones.loca.lt/api/"
        var retrofit: Retrofit? = null
        val tag= "APIC"


        private fun getClient(): Retrofit? {
            if (retrofit == null) {
                //val builder = UnsafeOkHttpClient.getUnsafeOkHttpClient()//OkHttpClient().newBuilder()
                //val okHttpClient = builder .build()


                /*val okHttpClient = OkHttpClient.Builder()
                okHttpClient.hostnameVerifier(HostnameVerifier { hostname, session -> //return true;
                    val hv: HostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()
                    hv.verify("*.herokuapp.com", session)
                })*/
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //.client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        suspend fun login(userCredentials: UserCredentials) : Result<Any> { //Result<LoggedInUser?

            val parameters: HashMap<String, String> = HashMap()
            parameters["user[rfc]"] = userCredentials.rfc;
            parameters["user[password]"] = userCredentials.password;

            parameters["device[platform]"] = "android";
            parameters["device[uuid]"] = userCredentials.deviceId;
            parameters["device[model]"] = userCredentials.deviceName;
            parameters["device[token]"] = userCredentials.fcmToken;



            try {
                //val call = getClient()?.create(ApiInterface::class.java)?.login(userCredentials.rfc,userCredentials.password)
                val call = getClient()?.create(ApiInterface::class.java)?.login(parameters)

                Log.d(tag, call?.isSuccessful.toString())
                Log.d(tag, call?.body().toString())
                Log.d(tag, call?.errorBody().toString())






                return if(call?.isSuccessful == true) {
                    //show Recyclerview
                    val loggedInUserResponse = call.body()
                    Log.d(tag, "User found")
                    Log.d(tag, loggedInUserResponse!!.loggedInUser.toString())
                    var loggedInUser=loggedInUserResponse!!.loggedInUser
                    loggedInUser.apiToken=loggedInUserResponse!!.token
                    Result.Success(loggedInUser)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Log.d(tag,  jObjError.getString("message"))
                    Result.Error(LoginException(jObjError.getString("message")))
                    //throw LoginException(jObjError.getString("message"))
                }
            }catch (ste: SocketTimeoutException){
                //throw NetworkException("Hay un problema con el servidor, no hay respuesta")
                Log.d(tag, "Error en la red "+ste.message)
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                Log.d(tag, "ocurrio un error desconocido "+e.message)
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }

        suspend fun registerDevice(userDevice: Device) : Result<Any> { //Result<LoggedInUser?

            val parameters: HashMap<String, String> = HashMap()
            parameters["device[platform]"] = userDevice.platform;
            parameters["device[uuid]"] = userDevice.deviceId;
            parameters["device[model]"] = userDevice.deviceName;
            parameters["device[token]"] = userDevice.fcmToken;



            try {
                val call = getClient()?.create(ApiInterface::class.java)?.registerDevice(parameters)

                Log.d(tag, call?.isSuccessful.toString())
                Log.d(tag, call?.body().toString())
                Log.d(tag, call?.errorBody().toString())

                return if(call?.isSuccessful == true) {
                    //show Recyclerview
                    val deviceRegisteredResponse = call.body()
                    Log.d(tag, deviceRegisteredResponse!!.message)
                    Result.Success(deviceRegisteredResponse)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Log.d(tag,  jObjError.getString("message"))
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                Log.d(tag, "Error en la red "+ste.message)
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                Log.d(tag, "ocurrio un error desconocido "+e.message)
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }


    }



    //var loggedInUser: LoggedInUser? = null // works



    /*call!! .enqueue(object : Callback<LoggedInUserResponse?> {
        override fun onResponse(call: Call<LoggedInUserResponse?>?, response: Response<LoggedInUserResponse?>) {
            Log.d("LOGIN", "isSuccessful" + response.errorBody().toString())
            if(response.isSuccessful()){
                val loggedInUser = response.isSuccessful /// .body()?.loggedInUser
                Log.d("LOGIN", "Number of movies received: " + loggedInUser)
                //Log.d("LOGIN", "Number of movies received: " + loggedInUser?.name)
            }else{
                Log.d("LOGIN", "ocurrio un errir " )
            }
        }

        override fun onFailure(call: Call<LoggedInUserResponse?>?, t: Throwable) {
            // Log error here since request failed
            Log.e("LOGIN", t.toString())
        }
    })*/

    //return loggedInUser



    //return Result.Error(NetworkException("Hay un problema con el servidor, No es posible hacer la peticion"))


}




