package com.example.meliapisdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.ProductItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductItemRepository {

    var productApi = RetrofitService().createService(ProductApi::class.java)

    fun getProductItem(id: String, liveData: MutableLiveData<ProductItem>){

        productApi.getProduct(id).enqueue(object : Callback<ProductItem>{
            override fun onFailure(call: Call<ProductItem>, t: Throwable) {
            }

            override fun onResponse(call: Call<ProductItem>, response: Response<ProductItem>) {
                if (response.isSuccessful) liveData.postValue(response.body())
            }

        })

    }

}