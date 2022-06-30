package com.iis.app.data


import android.util.Log
import java.io.UnsupportedEncodingException

import java.lang.Exception



class ISSUtils {
    companion object {
        @Throws(Exception::class)
        fun decoded(jwtEncoded: String) {
            try {
                val split = jwtEncoded.split("\\.").toTypedArray()
                Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
                Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
            } catch (e: UnsupportedEncodingException) {
                //Error
            }
        }

        @Throws(UnsupportedEncodingException::class)
        private fun getJson(strEncoded: String): String? {
           // val decodedBytes: ByteArray = android.util.Base64.decode(strEncoded, android.util.Base64.URL_SAFE)
            return android.util.Base64.decode(strEncoded, android.util.Base64.URL_SAFE).toString(charset("UTF-8"))
        }

    }
}