package com.example.dressmeup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dressmeup.databinding.CartViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CartViewFragment: BottomSheetDialogFragment() {

    private lateinit var cartAdapter: CartViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartItems: MutableList<CartItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.cart_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvCartList)

        // Initialize RecyclerView
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartViewAdapter(cartItems) // Ensure you have a RecyclerView Adapter named CartAdapter
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

        // Load data into the adapter if needed
        loadCartItems()
    }

    private fun loadCartItems() {
        cartItems = mutableListOf(
            CartItem(1, "Helmet", 2499.99, "https://example.com/images/helmet.jpg"),
            CartItem(2, "Riding Gloves", 999.50, "https://example.com/images/gloves.jpg"),
            CartItem(3, "Bike Jacket", 4999.00, "https://example.com/images/jacket.jpg"),
            CartItem(4, "Knee Guards", 1499.99, "https://example.com/images/knee_guards.jpg"),
            CartItem(5, "Riding Boots", 3999.49, "https://example.com/images/boots.jpg"),
            CartItem(6, "Motorcycle Cover", 799.99, "https://example.com/images/cover.jpg"),
            CartItem(7, "Phone Mount", 499.00, "https://example.com/images/phone_mount.jpg"),
            CartItem(8, "Tank Bag", 1999.99, "https://example.com/images/tank_bag.jpg"),
            CartItem(9, "Chain Lubricant", 350.00, "https://example.com/images/chain_lube.jpg"),
            CartItem(10, "LED Fog Lights", 2999.99, "https://example.com/images/fog_lights.jpg")
        ) // Replace with actual data
        cartAdapter.submitList(cartItems) // Use DiffUtil or notifyDataSetChanged if needed
    }

}