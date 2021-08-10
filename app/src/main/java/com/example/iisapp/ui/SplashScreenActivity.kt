package com.example.iisapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.iisapp.R
import com.example.iisapp.ui.login.LoginActivity


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref =  getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
        var userLoggedIn = sharedPref.getBoolean(getString(R.string.saved_user_logged_in), false)
        val token = sharedPref.getString(getString(R.string.saved_api_token), "nada")

        val intent = if(userLoggedIn){
            Intent(this, NavigationActivity::class.java).apply {}
        }else{
            Intent(this, LoginActivity::class.java).apply { }
        }
        startActivity(intent)
        finish()
    }
}