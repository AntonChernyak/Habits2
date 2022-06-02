package com.example.habits.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/*
object HabitApiClient {

    private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .authenticator(HabitAuthenticator())
        .build()

    private val contentType = "application/json".toMediaType()

    @ExperimentalSerializationApi
    val apiClient: HabitApiInterface by lazy {

        val  retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        return@lazy retrofit.create(HabitApiInterface::class.java)
    }

}*/
