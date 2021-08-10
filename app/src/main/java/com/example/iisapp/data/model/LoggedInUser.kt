package com.example.iisapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    //val userId: String,
    //val displayName: String

    @SerializedName("email") var email: String,
    @SerializedName("name") var name: String,
    @SerializedName("rfc") var rfc: String,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("iis_role") var iisRole: String,
    @SerializedName("picture_url") var pictureUrl: String,
    //@SerializedName("api_token") var apiToken: String
    var apiToken : String,

){
    override fun toString(): String {
        return "{email = $email, rfc = ${rfc}}"
    }
}