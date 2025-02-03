package com.example.feedarticles.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object ApiService {
    private fun getClient(): Retrofit{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val moshi = Moshi.Builder().apply {
            add(KotlinJsonAdapterFactory())
        }.build()

        return Retrofit.Builder().apply {
            baseUrl(ApiRoutes.BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(client)
        }.build()
    }

    fun getApi() = getClient().create(ApiInterface::class.java)
}