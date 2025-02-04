package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class UpdateItemDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "desc")
    val description: String,
    @Json(name = "image")
    val urlImage: String,
    @Json(name = "cat")
    val category: Int,
    @Json(name = "token")
    val token: String
)