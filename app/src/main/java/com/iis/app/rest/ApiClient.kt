package com.iis.app.rest


import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.iis.app.exceptions.LoginException

import com.iis.app.exceptions.NetworkException

import org.json.JSONObject
import java.net.SocketTimeoutException

import com.iis.app.data.Result
import com.iis.app.data.model.*
import com.google.gson.Gson

class ApiClient {


    companion object {
        //private const val baseUrl = "https://iis-notificaciones-api.herokuapp.com/api/"
        //private const val baseUrl = "https://notificaciones3.loca.lt/api/"
        private const val baseUrl = "https://notificaciones.sociales.unam.mx/api/app/"

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

         /*fun login2(userCredentials: UserCredentials) : Result<LoggedInUser> {
            var loggedInUser= LoggedInUser("csanchez@sociales.unam.mx","Carlos sanchez","sapc8704025",)

            Result.Success(loggedInUser)

        }*/
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


                return if(call?.isSuccessful == true) {
                    val loggedInUserResponse = call.body()
                    var loggedInUser=loggedInUserResponse!!.loggedInUser
                    loggedInUser.apiToken=loggedInUserResponse!!.token
                    Result.Success(loggedInUser)


                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
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

                return if(call?.isSuccessful == true) {
                    val deviceRegisteredResponse = call.body()
                    Result.Success(deviceRegisteredResponse)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }



        suspend fun getTramites(tramiteType: String,token: String) : Result<Any> {

            try {
                val call = getClient()?.create(ApiInterface::class.java)?.getTramites(tramiteType,token)

                return if(call?.isSuccessful == true) {
                    val tramitesResponse = call.body()
                    var tramites = tramitesResponse!!.tramites

                    //Log.d("TRAMITES",tramites.size.toString())
                    Result.Success(tramites)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }

        suspend fun registerToTramite(tramite: Tramite,token: String) : Result<Any> { //Result<LoggedInUser?

            val parameters: HashMap<String, String> = HashMap()
            parameters["tramite[id]"] = tramite.slug

            val gson = Gson()
            val dataToJson = gson.toJson(tramite.data)

            parameters["tramite[data]"] = dataToJson



            try {
                val call = getClient()?.create(ApiInterface::class.java)?.registerToTramite(parameters,token)

                return if(call?.isSuccessful == true) {
                    //show Recyclerview
                    val    tramiteRegisteredResponse = call.body()
                    var message = tramiteRegisteredResponse!!.message
                    Result.Success(message)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }



        suspend fun getSolicitudes(token: String) : Result<Any> { //Result<LoggedInUser?

            try {
                val call = getClient()?.create(ApiInterface::class.java)?.getSolicitudes(token)

                return if(call?.isSuccessful == true) {
                    val solicitudesResponse = call.body()
                    var solicitudes = solicitudesResponse!!.solicitudes
                    Result.Success(solicitudes)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }


        suspend fun getNotifications(token: String) : Result<Any> { //Result<LoggedInUser?

            try {
                val call = getClient()?.create(ApiInterface::class.java)?.getNotifications(token)

                return if(call?.isSuccessful == true) {
                    val notificationsResponse = call.body()
                    var notifications = notificationsResponse!!.notifications
                    Result.Success(notifications)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }


        suspend fun markAsSeen(id: Int,token: String) : Result<Any> {

            val parameters: HashMap<String, String> = HashMap()
            parameters["id"] = id.toString();
            try {
                val call = getClient()?.create(ApiInterface::class.java)?.markAsSeen(parameters,token)

                return if(call?.isSuccessful == true) {
                    //show Recyclerview
                    val notificationResponse = call.body()
                    Result.Success(notificationResponse!!.notification)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
                e.printStackTrace()
                return Result.Error(NetworkException("ocurrio un error desconocido"))
            }
        }

        suspend fun getNews(token: String) : Result<Any> {

            try {
                val call = getClient()?.create(ApiInterface::class.java)?.getNews(token)

                return if(call?.isSuccessful == true) {
                    val newsResponse = call.body()
                    var tramites = newsResponse!!.news
                    Result.Success(tramites)
                }else{
                    val jObjError = JSONObject(call?.errorBody()?.string())
                    Result.Error(LoginException(jObjError.getString("message")))
                }
            }catch (ste: SocketTimeoutException){
                return Result.Error(NetworkException("Hay un problema con el servidor, no hay respuesta"))
            }catch (e: Exception){
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




