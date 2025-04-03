package com.example.dressmeup

data class ImageResponse(
    val id: Int,
    val image_base64: String
)

data class LocalHostResponse(
    val images: List<ImageResponse>
)