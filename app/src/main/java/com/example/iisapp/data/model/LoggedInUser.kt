package com.example.iisapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    //val userId: String,
    //val displayName: String

    @SerializedName("email") var email: String,
    //@SerializedName("name") var name: String,
    //@SerializedName("rfc") var rfc: String,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("iis_role") var iisRole: String,
    @SerializedName("role") var role: String,
    @SerializedName("picture_url") var pictureUrl: String,

    @SerializedName("user_type") var userType: String,
   /* @SerializedName("department") var department: String,
    @SerializedName("floor") var floor: String,
    @SerializedName("hall") var hall: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("grade") var grade: String,
    @SerializedName("position") var position: String,
*/

    var apiToken : String,

){
    override fun toString(): String {
        return ""//""{email = $email, rfc = ${rfc}}"
    }
}