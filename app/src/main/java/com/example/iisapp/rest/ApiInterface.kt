package com.example.iisapp.rest


import com.example.iisapp.data.model.LoggedInUser


import com.example.iisapp.data.model.UserCredentials
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiInterface {

        @FormUrlEncoded
        @POST("login/")
        //suspend fun login(@Field("user[rfc]") rfc:String, @Field("user[password]") password:String): Response<LoggedInUserResponse>
        suspend fun login(@FieldMap fields: Map<String, String>): Response<LoggedInUserResponse>

        //open fun login(@Body user: UserCredentials): Call<LoggedInUserResponse?>?
        //suspend fun login(@FieldMap fields: Map<String, String> ): Call<LoggedInUserResponse?>
        //suspend fun login(@Field("user[rfc]") rfc:String,@Field("user[password]") password:String  ): Call<LoggedInUserResponse?>

        //open fun getTopRatedMovies(@Query("api_key") apiKey: String?): Call<MoviesResponse?>?
        //Call fun login(@Body user: UserCredentials): Response<LoggedInUserResponse?>?

        /*@GET("movie/top_rated")
        suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): Call<MoviesResponse?>?

        // suspend fun <MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

        @GET("movie/{id}")
        Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String

        */

}