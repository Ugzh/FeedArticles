package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class RegisterAndLoginDto(
    @Json(name = "login")
    val login: String,
    @Json(name = "mdp")
    val password: String,
)