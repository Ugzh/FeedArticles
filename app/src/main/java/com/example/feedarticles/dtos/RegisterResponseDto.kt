package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class RegisterResponseDto(
    @Json(name = "status")
    val status: Int,
    @Json(name = "id")
    val idUser: Long,
    @Json(name = "token")
    val token: String?
)