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

        @FormUrlEncoded
        @POST("tramites_users/")
        suspend fun registerToTramite(@FieldMap fields: Map<String, String>,@Header("Authorization") token: String): Response<TramiteRegisteredResponse>


        @GET("tramites_users/")
        suspend fun getSolicitudes(@Header("Authorization") token: String): Response<SolicitudesResponse>

        @GET("notifications/")
        suspend fun getNotifications(@Header("Authorization") token: String): Response<NotificationsResponse>

        @FormUrlEncoded
        @PUT("mark-as-seen/")
        suspend fun markAsSeen(@FieldMap fields: Map<String, String>,@Header("Authorization") token: String): Response<NotificationResponse>


}