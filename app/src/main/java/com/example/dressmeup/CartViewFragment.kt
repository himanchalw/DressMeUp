package com.example.dressmeup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dressmeup.databinding.CartViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class CartViewFragment: BottomSheetDialogFragment() {

    private lateinit var cartAdapter: CartViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartItems: MutableList<CartItem>
    private lateinit var imageItems: ArrayList<ImageItem>
    private lateinit var cartItemLayout: View
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartItemLayout = inflater.inflate(R.layout.cart_item, container, false)
        return inflater.inflate(R.layout.cart_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageItems= arrayListOf()
        recyclerView = view.findViewById(R.id.rvCartList)

        // ✅ Get cart items from arguments
        cartItems = arguments?.getParcelableArrayList("cart_items") ?: mutableListOf()

        // ✅ Observe image data and update UI
        cartViewModel.imageItems.observe(viewLifecycleOwner, Observer { fetchedImages ->
            updateImageUrls(fetchedImages)
            cartAdapter.notifyDataSetChanged()
        })

        // ✅ Fetch images only if not already fetched
        cartViewModel.fetchImages()

        // Initialize RecyclerView
        setupRecyclerView()

        val cartBtn:ImageButton=cartItemLayout.findViewById(R.id.cartButton)
        var inCart=false
        cartBtn.setOnClickListener {
            inCart=!inCart
            it.setBackgroundResource(if (inCart) R.drawable.added_to_cart else R.drawable.cart_icon)
        }
    }

    private fun setupRecyclerView() {
        loadCartItems()
        cartAdapter = CartViewAdapter(cartItems) // Ensure you have a RecyclerView Adapter named CartAdapter
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

        // Load data into the adapter if needed
    }

    private fun updateImageUrls(images: List<ImageItem>) {
        cartItems.forEachIndexed { index, cartItem ->
            if (index < images.size) {
                cartItem.imageUrl = images[index].imageUrl
            }
        }
    }

    private fun loadCartItems() {
        cartItems = mutableListOf(
            CartItem(1, "Shirt", 2499.99, "https://example.com/images/helmet.jpg"),
            CartItem(2, "Trousers", 999.50, "https://example.com/images/gloves.jpg"),
            CartItem(3, "Tie", 4999.00, "https://example.com/images/jacket.jpg"),
            CartItem(4, "Loafer shoes", 1499.99, "https://example.com/images/knee_guards.jpg"),
            CartItem(5, "Black Belt", 3999.49, "https://example.com/images/boots.jpg"),
            CartItem(6, "Motorcycle Cover", 799.99, "https://example.com/images/cover.jpg"),
            CartItem(7, "Phone Mount", 499.00, "https://example.com/images/phone_mount.jpg"),
            CartItem(8, "Tank Bag", 1999.99, "https://example.com/images/tank_bag.jpg"),
            CartItem(9, "Chain Lubricant", 350.00, "https://example.com/images/chain_lube.jpg"),
            CartItem(10, "LED Fog Lights", 2999.99, "https://example.com/images/fog_lights.jpg")
        ) // Replace with actual data

//        imageItems.let { urls ->
//            cartItems.forEachIndexed { index, cartItem ->
//                if (index < urls.size) {
//                    cartItem.imageUrl = urls[index].imageUrl
//                }
//            }
//        }
        if (imageItems.isNotEmpty()) {
            cartItems.forEachIndexed { index, cartItem ->
                if (index < imageItems.size) {
                    cartItem.imageUrl = imageItems[index].imageUrl
                }
            }
        }
    }
    companion object {
        // ✅ Factory Method to Create a Fragment with Arguments
        fun newInstance(cartItems: List<CartItem>): CartViewFragment {
            val fragment = CartViewFragment()
            val bundle = Bundle().apply {
                putParcelableArrayList("cart_items", ArrayList(cartItems))
            }
            fragment.arguments = bundle
            return fragment
        }
    }

}