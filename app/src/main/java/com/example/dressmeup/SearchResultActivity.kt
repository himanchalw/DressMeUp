package com.example.dressmeup

import ImageAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class SearchResultActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // 🔹 Get RecyclerView from XML
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        lifecycleScope.launch {
            val posts = GetImages().fetchData() // 🔹 Fetch data from API
            if (posts != null) {
                val imageItems = posts.map { post -> ImageItem(post.urls.regular) } // 🔹 Convert Post to ImageItem
                recyclerView.adapter = ImageAdapter(imageItems) // 🔹 Attach adapter
            }
        }
    }
}