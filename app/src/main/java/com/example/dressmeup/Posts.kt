package com.example.dressmeup

data class Post(
    val id: String,
    val altDescription:String,
    val urls: Urls
)
data class Urls(
    val regular: String
)