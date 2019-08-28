package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.networking.ProductRepository

class ProductViewModel : ViewModel() {

    var mutableLiveData: MutableLiveData<ProductResponse>
    val productRepository: ProductRepository

    init {
        productRepository = ProductRepository
        mutableLiveData = MutableLiveData()

    }

    fun getProductReposioryFromSearch(search: String): LiveData<ProductResponse> {
        mutableLiveData = productRepository.getProducts(search)
        return mutableLiveData
    }
}