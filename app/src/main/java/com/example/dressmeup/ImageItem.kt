package com.example.dressmeup

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageItem(var imageUrl: String) : Parcelable
