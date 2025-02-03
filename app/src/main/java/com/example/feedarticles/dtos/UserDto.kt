package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class UserDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "login")
    val login: String,
    @Json(name = "mdp")
    val mdp: String,
    @Json(name = "token")
    val token: String
)