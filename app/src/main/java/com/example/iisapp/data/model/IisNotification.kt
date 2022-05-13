package com.example.iisapp.data.model

data class IisNotification (
    val id:Int,
    val title:String,
    val message:String,
    val url:String,
    val number:String,
    val date:String,
    val status:String,
    val notificationType:String,
    val originType:String,
    val sender:String,
    val initials:String

){
    companion object{
        val data
            get() = listOf(
                IisNotification(
                    1,
                    "Mensaje de la comunidad",
                "lorem ipsum",
                    "www.iis.unam.mx",
                    "1",
                    "29 nov 2021",
                    "unseen",
                    "alert",
                    "direccion",
                    "Direccion",
                    "Dir"


        ))
    }
}