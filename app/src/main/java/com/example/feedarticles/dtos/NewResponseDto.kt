package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class NewResponseDto(
    @Json(name = "status")
    val status: Int
)