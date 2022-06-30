package com.iis.app.fcm

import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import com.iis.app.data.Result
import com.iis.app.data.model.Device
import com.iis.app.rest.ApiClient
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.security.MessageDigest

class IISFirebaseMessagingService : FirebaseMessagingService() {
    private val tag="IISFMS"

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //Log.d(tag, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            //Log.d(tag, "Message data payload: ${remoteMessage.data}")
            Toast.makeText(baseContext, "notificacion con data",Toast.LENGTH_LONG)
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob()

            } else {
                // Handle message within 10 seconds
                //handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            //Log.d(tag, "Message Notification Body: ${it.body}")
            /*Looper.prepare()
            Handler().post(){
                Toast.makeText(baseContext, "notificacion recibida",Toast.LENGTH_LONG)
            }*/

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(baseContext, "notificacion recibida", Toast.LENGTH_LONG).show()
            }

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        //Log.d(tag, "Refreshed token: $token")

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
        //Log.d(tag, "sendRegistrationTokenToServer($token)")
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
            //Log.d(tag, "identifier "+identifier)
            return identifier
        } catch (e: Exception) {
            //Log.e("TAG", e.toString())
        }
        return ""
    }
}