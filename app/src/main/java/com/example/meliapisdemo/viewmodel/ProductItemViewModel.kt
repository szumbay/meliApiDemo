package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.meliapisdemo.model.Description
import com.example.meliapisdemo.model.ProductItem
import com.example.meliapisdemo.networking.DescriptionRepository
import com.example.meliapisdemo.networking.ProductItemRepository
import java.net.ProtocolException

class ProductItemViewModel : ViewModel() {
    private var productLiveData : MutableLiveData<ProductItem> = MutableLiveData()
    private var descriptionLiveData : MutableLiveData<Description> = MutableLiveData()



    fun getProduct(id: String) : MutableLiveData<ProductItem>{
        descriptionLiveData = getProductDescription(id)
        productLiveData = getProductItem(id)
        val product : LiveData<ProductItem> = Transformations.switchMap(descriptionLiveData){ description ->
            attachDescription(description, productLiveData)}
        return product as MutableLiveData<ProductItem>

    }

    fun getProductItem(id: String) : MutableLiveData<ProductItem>{
        ProductItemRepository.getProductItem(id, productLiveData)
        return productLiveData
    }

    fun getProductDescription(id: String) : MutableLiveData<Description>{
        DescriptionRepository.getProductDescription(id,descriptionLiveData)
        return descriptionLiveData
    }

    fun attachDescription(description: Description, productLiveData: MutableLiveData<ProductItem>) : LiveData<ProductItem>{
        productLiveData.value?.description = description.description
        return productLiveData
    }

}