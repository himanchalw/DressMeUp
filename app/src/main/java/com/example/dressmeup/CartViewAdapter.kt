package com.example.dressmeup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CartViewAdapter(private val cartList: List<CartItem>): RecyclerView.Adapter<CartViewAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater: LayoutInflater =LayoutInflater.from(parent.context)
            val view: View = inflater.inflate(R.layout.cart_item,parent,false)

        return CartViewHolder(view)
    }
    class CartViewHolder(itemView: View): ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView =itemView.findViewById(R.id.itemPrice)
        val itemImage: ImageView =itemView.findViewById(R.id.itemImage)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.itemName.text=cartList[position].itemName
        holder.itemPrice.text= cartList[position].itemPrice.toString()
        holder.itemImage.setImageResource(cartList[position].imageUrl)
    }
}