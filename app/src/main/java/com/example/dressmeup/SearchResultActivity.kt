package com.example.dressmeup

import ImageAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dressmeup.databinding.ResultPageBinding
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class SearchResultActivity:AppCompatActivity() {
    private lateinit var binding: ResultPageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var wishlistToggle:Boolean = false
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
            val intent = intent
            val receivedPrompt = intent.getStringExtra("prompt")
            val posts = GetImages().fetchData(receivedPrompt) // 🔹 Fetch data from API
            posts?.let { safePosts ->  // 🔹 Use 'let' to ensure non-null value
                val imageItems = safePosts.take(3).map { post -> ImageItem(post.image_base64) }
                adapter.updateData(imageItems)
            }
        }
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            for (i in 0 until bottomNavigationView.menu.size()) {
                val menuItem = bottomNavigationView.menu.getItem(i)
                if (menuItem.itemId == item.itemId) {
                    val itemView = bottomNavigationView.findViewById<View>(menuItem.itemId)

                    itemView?.animate()
                        ?.scaleX(1.2f)
                        ?.scaleY(1.2f)
                        ?.setDuration(100)
                        ?.withEndAction {
                            itemView.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .duration = 100
                        }
                    break
                }
            }
            when (item.itemId) {
                R.id.expand_view -> {
                    // Handle "Choose Look" click
                    val bottomSheetFragment = CartViewFragment()
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)


                    true
                }
                R.id.wishlist -> {
                    item.icon = ContextCompat.getDrawable(
                        this,
                        if (wishlistToggle) R.drawable.added_to_wishlist else R.drawable.wishlist_icon
                    )
                    true
                }
                R.id.Refresh -> {
                    // Handle Refresh action
                    lifecycleScope.launch {
                        val intent = intent
                        val receivedPrompt = intent.getStringExtra("prompt")
                        val posts = GetImages().fetchData(receivedPrompt) // 🔹 Fetch data from API
                        posts?.let { safePosts ->  // 🔹 Use 'let' to ensure non-null value
                            val imageItems = safePosts.take(3).map { post -> ImageItem(post.image_base64) }
                            adapter.updateData(imageItems)
                        }
                    }
                    true
                }
                else -> false
            }
        }

    }
}