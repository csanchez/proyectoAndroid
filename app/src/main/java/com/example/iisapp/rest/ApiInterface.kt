package com.example.iisapp.rest


import com.example.iisapp.data.model.LoggedInUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

import com.example.iisapp.data.model.UserCredentials


interface ApiInterface {


    @POST("login/")
    //suspend fun login(@Body user: UserCredentials): Response<LoggedInUserResponse?>?
    open fun login(@Body user: UserCredentials): Call<LoggedInUserResponse?>?

    //open fun getTopRatedMovies(@Query("api_key") apiKey: String?): Call<MoviesResponse?>?
    //Call fun login(@Body user: UserCredentials): Response<LoggedInUserResponse?>?

    /*@GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): Call<MoviesResponse?>?

    // suspend fun <MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String

    */

}