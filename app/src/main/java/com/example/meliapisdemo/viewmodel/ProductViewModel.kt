package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.product.ProductResponse
import com.example.meliapisdemo.networking.repos.ProductRepository
import com.example.meliapisdemo.utils.InternetUtils
import java.io.Serializable

class ProductViewModel : ViewModel(), Serializable {

    var mutableLiveData: MutableLiveData<ProductResponse> = MutableLiveData()

    fun getProductRepository(search: String): MutableLiveData<ProductResponse> {
        ProductRepository.getProducts(search, mutableLiveData)
        return mutableLiveData
    }


}