package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.ProductDTO
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.networking.ProductRepository

class ProductViewModel : ViewModel() {

    var mutableLiveData: MutableLiveData<ProductResponse>
    val productRepository = ProductRepository

    init {
        mutableLiveData = MutableLiveData()
    }

    fun getProductRepository(search: String): MutableLiveData<ProductResponse> {
        mutableLiveData = productRepository.getProducts(search)
        return mutableLiveData
    }
}