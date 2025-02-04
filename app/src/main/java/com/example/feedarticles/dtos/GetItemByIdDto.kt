package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class GetItemByIdDto(
    @Json(name = "status")
    val status: String,
    @Json(name = "article")
    val item: ItemDto
)