package ru.farkhodkhaknazarov.atlant.api

import ru.farkhodkhaknazarov.atlant.api.services.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.farkhodkhaknazarov.atlant.utils.Constants

class ApiServiceImpl {

    lateinit var apiService: ApiService

    fun getApiServiceInstance(): ApiService {

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        return apiService
    }
}