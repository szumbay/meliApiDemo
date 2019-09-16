package com.example.meliapisdemo.viewmodel

import androidx.lifecycle.*
import com.example.meliapisdemo.model.productItem.*
import com.example.meliapisdemo.networking.repos.DescriptionRepository
import com.example.meliapisdemo.networking.repos.ProductItemRepository
import java.io.Serializable

class ProductItemViewModel : ViewModel(), Serializable{
    var productLiveData : MutableLiveData<ProductItemResponse> = MutableLiveData()
    var descriptionLiveData : MutableLiveData<DescriptionResponse> = MutableLiveData()

    val mediatorLiveData = MediatorLiveData<ProductDetailResponse>()

    fun getProduct(id: String) : LiveData<ProductDetailResponse>{

        ProductItemRepository.getProductItem(id, productLiveData)
        DescriptionRepository.getProductDescription(id,descriptionLiveData)

        mediatorLiveData.addSource(productLiveData){
            mediatorLiveData.value = combineLatestData(descriptionLiveData, productLiveData)
        }
        mediatorLiveData.addSource(descriptionLiveData){
            mediatorLiveData.value = combineLatestData(descriptionLiveData, productLiveData)
        }

        return mediatorLiveData
    }

    private fun combineLatestData( descriptionResult: LiveData<DescriptionResponse>,
                                   productItemResult: LiveData<ProductItemResponse>): ProductDetailResponse {

        val description = descriptionResult.value
        val productItem = productItemResult.value

        // Don't send a response until we have both results
        if (description == null || productItem == null) {
            return ProductDetailResponse.Loading("loading data")
        }

        return ProductDetailResponse.Response(description,productItem)
    }

}