package com.example.feedarticles.network

import com.example.feedarticles.dtos.GetAllItemsDto
import com.example.feedarticles.dtos.GetItemByIdDto
import com.example.feedarticles.dtos.NewItemDto
import com.example.feedarticles.dtos.NewResponseDto
import com.example.feedarticles.dtos.RegisterAndLoginResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @FormUrlEncoded
    @POST(ApiRoutes.REGISTER)
    fun registerUser(@Field("login") login: String, @Field("mdp") password: String) : Call<RegisterAndLoginResponseDto>?

    @FormUrlEncoded
    @POST(ApiRoutes.LOGIN)
    fun loginUser(@Field("login") login: String, @Field("mdp") password: String) : Call<RegisterAndLoginResponseDto>?

    @GET(ApiRoutes.ITEMS)
    fun getAllItems(@Query("token") token: String) : Call<GetAllItemsDto>?

    @GET(ApiRoutes.ITEM)
    fun getItemById(@Query("id") idItem : Long ,@Query("token") token: String) : Call<GetItemByIdDto>?

    //{"id_u":1,"title":"test","desc":"test","image":"http","cat":1,"token":"t3s7"}
    @POST(ApiRoutes.NEW)
    fun createNewItem(@Body newItemDto: NewItemDto) : Call<NewResponseDto>?
}