package ru.farkhodkhaknazarov.atlant.utils

import android.util.Base64
import com.google.gson.GsonBuilder
import org.json.JSONObject
import ru.farkhodkhaknazarov.atlant.api.entities.Token
import ru.farkhodkhaknazarov.atlant.data.room.entities.UserTokens
import java.io.UnsupportedEncodingException
import java.util.*


class JWTUtils : Exception() {
    companion object {
        fun decoded(tokenResp: Token?): UserTokens? {
            try {
                val split = tokenResp?.token?.split(".")

                if(split==null)
                    return null

                val tokenJSON: String = getJson(split!![1])

                return readTokenJSON(tokenJSON)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                return null
            }
        }

        private fun readTokenJSON(tokenJSON: String): UserTokens? {
            val obj:JSONObject = JSONObject(tokenJSON)
            var gson = GsonBuilder().create()

            val userToken = gson.fromJson<UserTokens>(obj.toString(), UserTokens::class.java)
            userToken.exp += Date().getTime()
            return userToken

        }

        @Throws(UnsupportedEncodingException::class)
        private fun getJson(strEncoded: String): String {
            val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
            return String(decodedBytes)
        }
    }

}