package com.example.iisapp.fcm

import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.iisapp.data.Result
import com.example.iisapp.data.model.Device
import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.rest.ApiClient
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.security.MessageDigest

class IISFirebaseMessagingService : FirebaseMessagingService() {
    val tag="IISFMS"

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(tag, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(tag, "sendRegistrationTokenToServer($token)")
        val device = token?.let { Device("android",getDeviceId(),getDeviceName(), it) }

        GlobalScope.launch(Dispatchers.Main) {
            val result = device?.let { ApiClient.registerDevice  (it) }

            if (result is Result.Success) {
                Toast.makeText(baseContext, "asdasdasdasdasdasd", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.lowercase().startsWith(manufacturer.lowercase())) {
            model.uppercase()
        } else {
            manufacturer.uppercase()+ " " + model
        }
    }

    private fun getDeviceId(): String {

        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        val pseudoId = "35" +
                Build.BOARD.length % 10 +
                Build.BRAND.length % 10 +
                //Build.SUPPORTED_ABIS[0].length % 10 +
                Build.DEVICE.length % 10 +
                Build.DISPLAY.length % 10 +
                Build.HOST.length % 10 +
                Build.ID.length % 10 +
                Build.MANUFACTURER.length % 10 +
                Build.MODEL.length % 10 +
                Build.PRODUCT.length % 10 +
                Build.TAGS.length % 10 +
                Build.TYPE.length % 10 +
                Build.USER.length % 10 //13 digits

        val wm = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val wlanMac = wm.connectionInfo.macAddress

        val uniqueId = androidId+pseudoId+wlanMac

        try {
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(uniqueId.toByteArray(), 0, uniqueId.length)

            // get md5 bytes
            val md5Bytes = messageDigest.digest()

            // creating a hex string
            var identifier = ""
            for (md5Byte in md5Bytes) {
                val b = 0xFF and md5Byte.toInt()

                // if it is a single digit, make sure it have 0 in front (proper padding)
                if (b <= 0xF) {
                    identifier += "0"
                }

                // add number to string
                identifier += Integer.toHexString(b)
            }

            // hex string to uppercase
            identifier = identifier.uppercase()
            Log.d(tag, "identifier "+identifier)
            return identifier
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
        return ""
    }
}