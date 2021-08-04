package com.example.iisapp.rest


import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.UserCredentials
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log
import okhttp3.OkHttpClient
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession
import android.graphics.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection


class ApiClient {


    companion object {
        private const val baseUrl = "https://iis-notificaciones-api.herokuapp.com/api/"
         var retrofit: Retrofit? = null


        private fun getClient(): Retrofit? {
            if (retrofit == null) {
                //val builder = UnsafeOkHttpClient.getUnsafeOkHttpClient()//OkHttpClient().newBuilder()
                //val okHttpClient = builder .build()


                val okHttpClient = OkHttpClient.Builder()
                okHttpClient.hostnameVerifier(HostnameVerifier { hostname, session -> //return true;
                    val hv: HostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()
                    hv.verify("*.herokuapp.com", session)
                })




                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        suspend fun login(userCredentials: UserCredentials) : LoggedInUser? {
            Log.d("LOGIN", userCredentials.username)
            val call = ApiClient.getClient()?.create(ApiInterface::class.java)?.login(userCredentials)
            var loggedInUser: LoggedInUser? = null // works

            call!!.enqueue(object : Callback<LoggedInUserResponse?> {
                override fun onResponse(call: Call<LoggedInUserResponse?>?, response: Response<LoggedInUserResponse?>) {
                    val loggedInUser = response.isSuccessful /// .body()?.loggedInUser
                    Log.d("LOGIN", "Number of movies received: " + loggedInUser)
                    //Log.d("LOGIN", "Number of movies received: " + loggedInUser?.name)
                }

                override fun onFailure(call: Call<LoggedInUserResponse?>?, t: Throwable) {
                    // Log error here since request failed
                    Log.e("LOGIN", t.toString())
                }
            })

            return loggedInUser

           /* val loggedInUserResponse = call?.execute()?.body()
            Log.d("LOGIN", loggedInUserResponse?.loggedInUser.toString())
            if (call != null) {

                Log.d("LOGIN", "User found")
                Log.d("LOGIN", loggedInUserResponse?.loggedInUser.toString())
                if(call.isExecuted){
                    //show Recyclerview
                    Log.d("LOGIN", "User found")
                    Log.d("LOGIN", loggedInUserResponse?.loggedInUser.toString())

                }else{
                    Log.d("LOGIN", "error")
                }
            }else{
                Log.d("LOGIN", "call is null")
            }
            return loggedInUserResponse?.loggedInUser;*/
        }
    }


    }




