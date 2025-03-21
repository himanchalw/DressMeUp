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
        bottomNavigationView=binding.bottomNavigationView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // 🔹 Get RecyclerView from XML
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        val adapter = ImageAdapter(mutableListOf())
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            val posts = GetImages().fetchData(3) // 🔹 Fetch data from API
            posts?.let { safePosts ->  // 🔹 Use 'let' to ensure non-null value
                val imageItems = safePosts.take(3).map { post -> ImageItem(post.urls.regular) }
                adapter.updateData(imageItems)
            }
        }
        val bottomExpandButton=bottomNavigationView.menu.findItem(R.id.expand_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.expand_view -> {
                    // Handle "Choose Look" click
                    val bottomSheetFragment = CartViewFragment()
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                    lifecycleScope.launch {
                        val items = GetImages().fetchData(10)
                        val temp = items?.map { post -> ImageItem(post.urls.regular) } // 🔹 Convert Post to ImageItem
//                if (temp != null) {
//                    val bundle = Bundle()
//
//                    bundle.putParcelableArrayList("cart_items", ArrayList(temp)) // 🔹 Convert List to ArrayList
//
//                    bottomSheetFragment.arguments = bundle
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container_view, CartViewFragment()) // Replaces any existing fragment
//                        .addToBackStack(null) // Allows back navigation
//                        .commit()
//                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
//                }
                        if (temp != null) {
                            val bundle = Bundle()
                            bundle.putParcelableArrayList("cart_items", ArrayList(temp))
                            bottomSheetFragment.arguments = bundle
                            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                        }
                    }
                    true
                }
                R.id.wishlist -> {
                    // Handle Wishlist action
                    true
                }
                R.id.settings -> {
                    // Handle Refresh action
                    true
                }
                else -> false
            }
        }

    }
}