package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.networking.ProductRepository

class ProductViewModel : ViewModel() {

    val mutableLiveData: MutableLiveData<ProductResponse>
    val productRepository: ProductRepository

    init {
        productRepository = ProductRepository
        mutableLiveData = productRepository.getProducts("macbook")

    }

    fun getProductRepository(): LiveData<ProductResponse> {
        return mutableLiveData
    }
}