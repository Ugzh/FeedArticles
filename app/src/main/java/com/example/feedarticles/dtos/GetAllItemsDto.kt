package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class GetAllItemsDto(
    @Json(name = "status")
    val status: String,
    @Json(name = "articles")
    val items: List<ItemDto>
)