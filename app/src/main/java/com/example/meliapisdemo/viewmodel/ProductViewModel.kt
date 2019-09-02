package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.ProductDTO
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.networking.ProductRepository

class ProductViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<ProductResponse> = MutableLiveData()
    private val productRepository = ProductRepository

    fun getProductRepository(search: String): MutableLiveData<ProductResponse> {
        productRepository.getProducts(search, mutableLiveData)
        return mutableLiveData
    }
}