package com.example.dressmeup

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class CartViewAdapter(private val cartList: MutableList<CartItem>): RecyclerView.Adapter<CartViewAdapter.CartViewHolder>() {
    var inCart=false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater: LayoutInflater =LayoutInflater.from(parent.context)
            val view: View = inflater.inflate(R.layout.cart_item,parent,false)

        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position],position)
    }

    // Make ViewHolder 'inner' so it can access adapter variables
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        private val cartButton: ImageView = itemView.findViewById(R.id.cartButton)

        fun bind(cartItem: CartItem, position: Int) {
            itemName.text = cartItem.itemName
            itemPrice.text = cartItem.itemPrice.toString()

            when (position) {
                0 -> itemImage.setImageResource(R.drawable.img1)
                1 -> itemImage.setImageResource(R.drawable.img2)
                2 -> itemImage.setImageResource(R.drawable.img3)
                3 -> itemImage.setImageResource(R.drawable.img4)
                4 -> itemImage.setImageResource(R.drawable.img5)
            }

            // Set button based on state
            cartButton.setImageResource(
                if (cartItem.isInCart) R.drawable.added_to_cart else R.drawable.add_to_cart
            )

            cartButton.setOnClickListener {
                // Animate the button
                it.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(100)
                    .withEndAction {
                        it.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .duration = 100
                    }

                // Toggle state
                cartItem.isInCart = !cartItem.isInCart

                // Update background
                cartButton.setImageResource(
                    if (cartItem.isInCart) R.drawable.added_to_cart else R.drawable.add_to_cart
                )

                Log.i("CartViewAdapter", "Item ${cartItem.itemName} cart state: ${cartItem.isInCart}")
            }
        }
    }

}