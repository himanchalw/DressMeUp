package com.example.dressmeup

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*



class GetImages {
    private val client = OkHttpClient()
//    private var url = "https://api.unsplash.com/photos/random?client_id=IANVB9vk2uncx9k9LyFBSFy_lsUo4NL3caMY7MFRZzQ&count="
    private var url = "localhost:8080"
    suspend fun fetchData(imgNum:Int): MutableList<LocalHostResponse>? {
        return withContext(Dispatchers.IO) {
            try {
//                url+=imgNum.toString()
                val request = Request.Builder().url(url).get().build()
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) return@withContext null

                val jsonResponse = response.body?.string() ?: return@withContext null
//                val listType = object : TypeToken<List<Post>>() {}.type
                val listType = object : TypeToken<List<LocalHostResponse>>() {}.type
                return@withContext Gson().fromJson(jsonResponse, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }
    fun getUri():String{
        return url
    }
}