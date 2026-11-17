package com.example.mdp.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object CbrRetrofit {
    private const val BASE_URL =
        "https://www.cbr.ru/"

    val api: CbrApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                ScalarsConverterFactory.create()
            )
            .build()
            .create(CbrApiService::class.java)
    }
}