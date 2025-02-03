package com.example.feedarticles.network

import com.example.feedarticles.dtos.RegisterResponseDto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST(ApiRoutes.REGISTER)
    fun registerUser(@Field("login") login: String, @Field("mdp") password: String) : Call<RegisterResponseDto>?
}