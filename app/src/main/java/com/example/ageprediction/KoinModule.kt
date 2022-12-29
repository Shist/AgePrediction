package com.example.ageprediction

import com.example.ageprediction.Consts.BASE_URL
import com.example.ageprediction.ui.retrofit.AgifyApi
import com.example.ageprediction.ui.retrofit.RetrofitClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val koinModule = module {

    single {
        val retrofitClient: RetrofitClient = get()

        retrofitClient.retrofit(BASE_URL)
            .create(AgifyApi::class.java)
    }

    single { RetrofitClient(client = get()) }

    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

}