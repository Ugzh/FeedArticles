package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class DeleteUpdateNewResponseDto(
    @Json(name = "status")
    val status: Int
)