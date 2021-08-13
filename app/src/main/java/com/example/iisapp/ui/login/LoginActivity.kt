package com.example.iisapp.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.iisapp.ui.NavigationActivity
import com.example.iisapp.databinding.ActivityLoginBinding

import com.example.iisapp.R
import java.util.*

import android.util.Log
import android.provider.Settings.Secure
import android.net.wifi.WifiManager
import java.lang.Exception
import java.security.MessageDigest
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.UserCredentials


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    private val tag="LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFCMToken()


        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        username.filters = arrayOf<InputFilter>(AllCaps())

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                val userCredentials = UserCredentials(username.text.toString(), password.text.toString(),getDeviceId(), getDeviceName(),getFCMToken())
                when (actionId) {

                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(userCredentials)
//                        loginViewModel.login(
//                            username.text.toString(),
//                            password.text.toString(),
//                            getDeviceId(),
//                            getDeviceName()
//                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                val userCredentials = UserCredentials(username.text.toString(), password.text.toString(),getDeviceId(), getDeviceName(),getFCMToken())
                loginViewModel.login(userCredentials)
            }
        }



    }

    /*@RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getDeviceId(): String {
        val tm =  getBaseContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val tmDevice =  tm.imei
        val tmSerial = tm.simSerialNumber
        val androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)

        val deviceUuid = UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())
        val deviceId: String = deviceUuid.toString()
        return deviceId
    }*/

    //https://www.pocketmagic.net/android-unique-device-id/
    /*@RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("HardwareIds")*/

    private fun getDeviceId(): String {
        /*val tm =  baseContext.getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        val tmDevice :String = try {
            tm.imei
        }catch (e: Exception){
            "NoIMEI"
        }

        val tmSerial = try {
            tm.simSerialNumber
        }catch (e: Exception){
            "NoSerialNumber"
        }*/


        val androidId = Secure.getString(contentResolver, Secure.ANDROID_ID)

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

        /*val deviceUuid = UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())
        val deviceId: String = deviceUuid.toString()*/

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
            Log.d("LOGIN", "identifier "+identifier)
            return identifier
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
        return ""
    }

    /*@Synchronized
    fun getDeviceId(): String? {
        var uniqueID: String? = null
        //val prefUniqueId = Secure.getString(contentResolver, Secure.ANDROID_ID)
        val prefUniqueId = Secure.getString(contentResolver, Secure.ANDROID_ID)
        val wm = getSystemService(WIFI_SERVICE) as WifiManager
        val m_szWLANMAC = wm.connectionInfo.macAddress

        if (uniqueID == null) {
            val sharedPrefs = baseContext.getSharedPreferences(
                prefUniqueId, MODE_PRIVATE
            )
            uniqueID = sharedPrefs.getString(prefUniqueId, null)
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString()
                val editor: SharedPreferences.Editor = sharedPrefs.edit()
                editor.putString(prefUniqueId, uniqueID)
                editor.commit()
            }
        }
        return uniqueID
    }*/

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.lowercase().startsWith(manufacturer.lowercase())) {
            model.uppercase()
        } else {
            manufacturer.uppercase()+ " " + model
        }
    }

    private fun getFCMToken(): String{
        val sharedPref =  getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
        var fcmToken = sharedPref.getString(getString(R.string.saved_fcm_token), "")

        Log.w(tag, "token recuperado $fcmToken")
        return fcmToken+""
    }

    private fun redirectToNavigationActivity(){
        val intent = Intent(this, NavigationActivity::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    //private fun updateUiWithUser(model: LoggedInUserView) {
    private fun updateUiWithUser(model: LoggedInUser) {
        val sharedPref =  getSharedPreferences(getString(R.string.shared_preferences_name),Context.MODE_PRIVATE)
        val edit: SharedPreferences.Editor = sharedPref.edit()

        //edit.putString(getString(R.string.saved_api_token),  model.apiToken);
        //edit.putBoolean(getString(R.string.saved_user_logged_in), true);
        //edit.commit();
        with (sharedPref.edit()) {
            putString(getString(R.string.saved_api_token), model.apiToken)
            putString(getString(R.string.saved_user_rfc), model.rfc)
            putString(getString(R.string.saved_user_email), model.email)
            putString(getString(R.string.saved_user_name), model.name)
            putBoolean(getString(R.string.saved_user_logged_in), true)
            commit()

        }



        val sharedPref2 =  getPreferences(Context.MODE_PRIVATE)
        val userLoggedIn = sharedPref2.getBoolean(getString(R.string.saved_user_logged_in), false)

        Log.d(tag, "user login $userLoggedIn")

        Toast.makeText(applicationContext, "¡"+getString(R.string.welcome_text)+" "+model.name+"!", Toast.LENGTH_SHORT).show()
        redirectToNavigationActivity()




    }

    //private fun showLoginFailed(@StringRes errorString: Int) {
    private fun showLoginFailed( errorString: String) {
        Log.d(tag, "showLoginFailed ")
       Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}