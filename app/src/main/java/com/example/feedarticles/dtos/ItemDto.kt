package com.example.feedarticles.dtos


import com.squareup.moshi.Json

data class ItemDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "titre")
    val title: String,
    @Json(name = "descriptif")
    val description: String,
    @Json(name = "url_image")
    val urlImage: String,
    @Json(name = "categorie")
    val category: Int,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "id_u")
    val idU: Long
)