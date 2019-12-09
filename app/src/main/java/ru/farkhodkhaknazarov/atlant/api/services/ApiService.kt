package ru.farkhodkhaknazarov.atlant.api.services

import retrofit2.Call
import retrofit2.http.*
import ru.farkhodkhaknazarov.atlant.api.entities.Login
import ru.farkhodkhaknazarov.atlant.api.entities.Token
import ru.farkhodkhaknazarov.atlant.api.entities.UserInfo

interface ApiService {
//    @GET("/getUsers")
//    fun getUsers(): Call<List<User>>

    @POST("/accounts/auth")
    @Headers("Content-Type: application/json")
    fun saveUser(@Body data: Login): Call<Token>


    @POST("/accounts/sessions/end")
    @Headers("Content-Type: application/json")
    fun logoutUser(@Header("Authorization") token:String, @Body data: String): Call<String>

    @GET("/accounts/current")
    fun getUserInfo(@Header("Authorization") token:String): Call<UserInfo>

//    @GET("/accounts/current")
//    fun getUserInfo(@Header("Authorization") token:String): Call<String>

    @GET("/accounts/sessions/refresh")
    fun refreshUserSession(@Header("Authorization") token:String): Call<Token>

}