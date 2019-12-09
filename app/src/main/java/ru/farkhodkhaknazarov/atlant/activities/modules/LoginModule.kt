package ru.farkhodkhaknazarov.atlant.activities.modules

import retrofit2.Call
import retrofit2.Response
import ru.farkhodkhaknazarov.atlant.activities.presenters.MainActivityPresenter
import ru.farkhodkhaknazarov.atlant.api.ApiServiceImpl
import ru.farkhodkhaknazarov.atlant.api.entities.Login
import ru.farkhodkhaknazarov.atlant.api.entities.Token
import ru.farkhodkhaknazarov.atlant.api.entities.UserInfo
import ru.farkhodkhaknazarov.atlant.api.services.ApiService
import ru.farkhodkhaknazarov.atlant.data.room.entities.UserTokens
import ru.farkhodkhaknazarov.atlant.utils.JWTUtils

class LoginModule {
    lateinit var apiService: ApiService

    fun registerUser(login: Login): UserTokens? {

        apiService = ApiServiceImpl().getApiServiceInstance()

        var result: Call<Token> = apiService.saveUser(login)

        result.enqueue(object : retrofit2.Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    val token: UserTokens? = JWTUtils.decoded(responseBody)

                    token?.email = login.email
                    token?.pass = login.password
                    token?.token =responseBody?.token.toString()

                    LoginActivityModule.saveToken(null, token)

                } else {
                    LoginActivityModule.loginActivityModule.registrationError(response.message())

                    println(response.message())
                }

            }

            override fun onFailure(call: Call<Token>, throwable: Throwable) {
                println(throwable.message)
            }
        })

        return null
    }

    fun logoutUser(token: UserTokens) {

        apiService = ApiServiceImpl().getApiServiceInstance()

        var result: Call<String> = apiService.logoutUser("Bearer " + token.token, token.session_id)

        result.enqueue(object : retrofit2.Callback<String> {
            override fun onFailure(call: Call<String>, throwable: Throwable) {
                MainActivityPresenter.mainActivityPresenter?.showMessage("Logout error, try again later")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                MainActivityModule.deleteAllTokens()
                MainActivityModule.startLoginActivity()
            }
        })
    }

    fun getUserProfile(token: String){
        apiService = ApiServiceImpl().getApiServiceInstance()

        var result: Call<UserInfo> = apiService.getUserInfo("Bearer " + token)

        result.enqueue(object : retrofit2.Callback<UserInfo> {
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                val b = 0
            }

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {

                if (response.isSuccessful && response.body() != null) {
                    MainActivityPresenter.updateUserProfile(response.body()!!)
                }
            }
        })



//        var result: Call<String> = apiService.getUserInfo("Bearer " + token)
//
//        result.enqueue(object : retrofit2.Callback<String> {
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                val b = 0
//            }
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//
//                if (response.isSuccessful && response.body() != null) {
////                    MainActivityPresenter.updateUserProfile(response.body() as UserInfo)
//                }
//            }
//        })
    }

    fun refreshToken(token: UserTokens){
        apiService = ApiServiceImpl().getApiServiceInstance()

        var result: Call<Token> = apiService.refreshUserSession("Bearer " + token.token)

        result.enqueue(object : retrofit2.Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    val newToken: UserTokens? = JWTUtils.decoded(responseBody)

                    newToken?.email = token.email
                    newToken?.pass = token.pass
                    newToken?.token =responseBody?.token.toString()

                    LoginActivityModule.saveToken(null, token)

                } else {
                    println(response.message())
                }

            }

            override fun onFailure(call: Call<Token>, throwable: Throwable) {
                println(throwable.message)
            }
        })

    }
}