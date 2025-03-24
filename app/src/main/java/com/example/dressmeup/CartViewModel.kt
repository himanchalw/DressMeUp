package com.example.dressmeup

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _imageItems = MutableLiveData<List<ImageItem>>() // Mutable list to store fetched images
    val imageItems: LiveData<List<ImageItem>> get() = _imageItems // Expose as immutable LiveData
    private var isDataFetched = false
    // Function to fetch images
    fun fetchImages() {
        if (_imageItems.value.isNullOrEmpty()) { // Fetch only if data is empty
            viewModelScope.launch {
                val fetchedImages = GetImages().fetchData(10)?.map { post -> ImageItem(post.urls.regular) } ?: emptyList()
                _imageItems.postValue(fetchedImages) // Update LiveData
                isDataFetched = true
            }
        }
    }
}
