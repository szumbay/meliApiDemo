package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.product.ProductResponse
import com.example.meliapisdemo.networking.repos.ProductRepository

class ProductViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<ProductResponse> = MutableLiveData()

    fun getProductRepository(search: String): MutableLiveData<ProductResponse> {
        ProductRepository.getProducts(search, mutableLiveData)
        return mutableLiveData
    }
}