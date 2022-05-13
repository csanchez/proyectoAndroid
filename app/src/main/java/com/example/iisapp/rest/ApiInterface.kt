package com.example.iisapp.rest


import com.example.iisapp.data.model.LoggedInUser


import com.example.iisapp.data.model.UserCredentials
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

        @FormUrlEncoded
        @POST("login/")
        suspend fun login(@FieldMap fields: Map<String, String>): Response<LoggedInUserResponse>

        @FormUrlEncoded
        @POST("devices/")
        suspend fun registerDevice(@FieldMap fields: Map<String, String>): Response<DeviceRegisteredResponse>

        @GET("tramites/")
        suspend fun getTramites(@Query("tipo") tipo: String?,@Header("Authorization") token: String): Response<TramitesResponse>


}