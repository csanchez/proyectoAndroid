package com.iis.app.rest


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
        suspend fun getTramites(@Query("tipoTramite") tipo: String?,@Header("Authorization") token: String): Response<TramitesResponse>

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

        @GET("news/")
        suspend fun getNews(@Header("Authorization") token: String): Response<NewsResponse>

        @GET("reservations/")
        suspend fun getReservations(@Query("start") start: String,
                                    @Query("end") end: String,
                                    @Query("space") space: String,
                                    @Query("user") user: String,
                                    @Query("event") event: String,
                                    @Query("service") service: String,
                                    @Query("require_equip") requireEquipo: String,
                                    @Header("Authorization") token: String): Response<ReservationsResponse>





}