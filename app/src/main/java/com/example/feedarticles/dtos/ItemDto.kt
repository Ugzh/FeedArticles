package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class ItemDto(
    @Json(name = "titre")
    val title: String,
    @Json(name = "descriptif")
    val description: String,
    @Json(name = "categorie")
    val category: Int,
    @Json(name = "url_image")
    val url: String,
    @Json(name = "id")
    val id: Long,
    @Json(name = "created_at")
    val createdAt: String
)