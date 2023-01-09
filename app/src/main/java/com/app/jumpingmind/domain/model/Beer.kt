package com.app.jumpingmind.domain.model

data class Beer(
    val id: Int,
    val name: String,
    val image_url: String,
    val description: String,
    val first_brewed: String
)