
package com.example.iisapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.iisapp.data.LoginRepository
import com.example.iisapp.data.Result

import com.example.iisapp.R
import com.example.iisapp.data.model.LoggedInUser
import com.example.iisapp.data.model.UserCredentials
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(userCredentials: UserCredentials) {
        // can be launched in a separate asynchronous job

        //val result = loginRepository.login(username, password,deviceId,deviceName)
        Log.d("LOGINVM", "FCM TOKEN "+userCredentials.fcmToken)
        viewModelScope.launch {
            //val result = loginRepository.login(username, password,deviceId,deviceName,fcmToken)
            val result = loginRepository.login(userCredentials)

            if (result is Result.Success) {
                Log.d("LOGIN VM", "Success")
                _loginResult.value =LoginResult(success = result.data?.let { it })
               //old// _loginResult.value =LoginResult(success = result.data?.let { LoggedInUserView(displayName = it.name) })
            } else {
                Log.d("LOGIN", "Error")
                if (result is Result.Error) {
                    Log.d("LOGIN", "Success")
                    _loginResult.value = LoginResult(error = result.exception?.let { result.exception.message })
                }

                //_loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }


        /*if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = result.data?.let { LoggedInUserView(displayName = it.name) })
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }*/
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


}