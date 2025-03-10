package com.example.dressmeup

import ImageAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dressmeup.databinding.ResultPageBinding
import kotlinx.coroutines.launch

class SearchResultActivity:AppCompatActivity() {
    private lateinit var binding: ResultPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ResultPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // 🔹 Get RecyclerView from XML
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        val adapter = ImageAdapter(mutableListOf())
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            val posts = GetImages().fetchData() // 🔹 Fetch data from API
            if (posts != null) {
                val imageItems = posts.map { post -> ImageItem(post.urls.regular) } // 🔹 Convert Post to ImageItem
                adapter.updateData(imageItems)
            }
        }
    }
}