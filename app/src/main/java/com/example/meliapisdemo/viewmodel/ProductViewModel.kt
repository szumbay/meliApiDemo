package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.product.ProductResponse
import com.example.meliapisdemo.networking.repos.ProductRepository
import java.io.Serializable

class ProductViewModel : ViewModel(), Serializable {

    var mutableLiveData: MutableLiveData<ProductResponse> = MutableLiveData()
    private var call : Boolean = false
    private val repository = ProductRepository()


    fun getProductRepository(search: String): MutableLiveData<ProductResponse> {

        when(mutableLiveData.value){
            is ProductResponse.Success -> call = search.equals((mutableLiveData.value as ProductResponse.Success).productDTO.query)
        }

        if(mutableLiveData.value == null && !call) {
            repository.getProducts(search, mutableLiveData)
        }
        return mutableLiveData
    }

}