package com.example.dressmeup

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItem(
    val id: Int,
    val itemName: String,
    val itemPrice: Double,
    var imageUrl: String
):Parcelable