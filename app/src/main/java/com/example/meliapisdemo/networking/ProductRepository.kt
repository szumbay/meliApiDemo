package com.example.meliapisdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductRepository {

    private val productApi : ProductApi

    init {
        productApi = RetrofitService().createService(ProductApi::class.java)
    }

    fun getProducts(search: String) : MutableLiveData<ProductResponse> {

        val newsData = MutableLiveData<ProductResponse>()

        productApi.getProductList(search).enqueue(object : Callback<ProductResponse>{

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                newsData.postValue(null)
            }

            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) newsData.postValue(response.body())
            }
        })
        return newsData
    }

}