package com.example.dressmeup

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*

data class Post(
    val id: String,
    val altDescription:String,
    val urls: Urls
)
data class Urls(
    val regular: String
)

class GetImages {
    private val client = OkHttpClient()
    private val url = "https://api.unsplash.com/photos/random?client_id=IANVB9vk2uncx9k9LyFBSFy_lsUo4NL3caMY7MFRZzQ&count=3"

    suspend fun fetchData(): List<Post>? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).get().build()
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) return@withContext null

                val jsonResponse = response.body?.string() ?: return@withContext null
                val listType = object : TypeToken<List<Post>>() {}.type

                return@withContext Gson().fromJson(jsonResponse, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}