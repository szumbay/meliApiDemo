package com.example.meliapisdemo.networking.repos

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.productItem.ProductItem
import com.example.meliapisdemo.model.productItem.ProductItemResponse
import com.example.meliapisdemo.networking.api.ProductApi
import com.example.meliapisdemo.networking.api.RetrofitService
import com.example.meliapisdemo.utils.getErrorType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductItemRepository {

    var productApi = RetrofitService()
        .createService(ProductApi::class.java)

    fun getProductItem(id: String, liveData: MutableLiveData<ProductItemResponse>){

        productApi.getProduct(id).enqueue(object : Callback<ProductItem>{
            override fun onFailure(call: Call<ProductItem>, t: Throwable) {
                val error = getErrorType(t)
                liveData.postValue(ProductItemResponse.Error(error))
            }

            override fun onResponse(call: Call<ProductItem>, response: Response<ProductItem>) {
                if (response.isSuccessful) liveData.postValue(ProductItemResponse.Success(response.body()!!))
                else liveData.postValue(ProductItemResponse.Error(getErrorType(response.code())))
            }

        })

    }

}