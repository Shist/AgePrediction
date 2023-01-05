package com.example.ageprediction.ui.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface AgifyApi {

    @GET("/")
    suspend fun getAgeByName(@Query("name") name : String) : SearchItemJson

}