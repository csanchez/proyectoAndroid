package com.iis.app.ui

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.iis.app.R
import com.iis.app.ui.login.LoginActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class SplashScreenActivity : AppCompatActivity() {

    private val tag="SPLSA"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref =  getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
        var userLoggedIn = sharedPref.getBoolean(getString(R.string.saved_user_logged_in), false)


        val token = sharedPref.getString(getString(R.string.saved_api_token), "nada")



        if (checkGooglePlayServices()) {
            getFCMToken()
            chooseNextActivity(userLoggedIn)
            finish()
        } else {
            //You won't be able to send notifications to this device


                val builder = AlertDialog.Builder(this@SplashScreenActivity)
                builder.apply {
                    setTitle("Device doesn't have google play services")
                    setMessage("You won't be able to send notifications to this device")
                    setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->
                            chooseNextActivity(userLoggedIn)
                            finish()
                        })
                }
                builder.create().show()
        }


        //finish()

    }

    private fun chooseNextActivity(userLoggedIn:Boolean){
        val intent = if(userLoggedIn){
            Intent(this, NavigationActivity::class.java).apply {
            }
        }else{
            Intent(this, LoginActivity::class.java).apply { }
        }
        startActivity(intent)
    }

    private fun getFCMToken(){

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->


                if (!task.isSuccessful) {
                    Log.w(tag, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                //val msg = getString("valor", token)
                val sharedPref =  getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
                val edit: SharedPreferences.Editor = sharedPref.edit()
                with (sharedPref.edit()) {
                    putString(getString(R.string.saved_fcm_token), token)
                    commit()

                }

            })


    }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            // ask user to update google play services and manage the error.
             false
        } else {
            // 3

             true
        }
    }

    private fun notification(){
     //intent.getStringExtra()
    }
}