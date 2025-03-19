package com.example.dressmeup

import ImageAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dressmeup.databinding.ResultPageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class SearchResultActivity:AppCompatActivity() {
    private lateinit var binding: ResultPageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
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
        var posts:List<Post>?
        lifecycleScope.launch {
            posts = GetImages().fetchData(13) // 🔹 Fetch data from API
            posts?.let { safePosts ->  // 🔹 Use 'let' to ensure non-null value
                val imageItems = safePosts.take(3).map { post -> ImageItem(post.urls.regular) }
                adapter.updateData(imageItems)
            }
        }
        val bottomExpandButton:BottomNavigationView=bottomNavigationView.findViewById(R.id.expand_view)
        bottomExpandButton.setOnClickListener {
            val bottomSheetFragment = CartViewFragment()
            if(posts!=null){

            }
            val temp = posts.take(3).map { post -> ImageItem(post.urls.regular) } // 🔹 Convert Post to ImageItem
            adapter.updateData(temp)
            bottomSheetFragment.arguments = Bundle().apply { putString("url", getImages.getUri()) }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, CartViewFragment()) // Replaces any existing fragment
                .addToBackStack(null) // Allows back navigation
                .commit()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

    }
}