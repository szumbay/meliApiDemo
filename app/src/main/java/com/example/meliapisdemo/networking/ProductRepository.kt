package com.example.meliapisdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.ProductDTO
import com.example.meliapisdemo.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.IllegalArgumentException

object ProductRepository {

    private val productApi : ProductApi

    init {
        productApi = RetrofitService().createService(ProductApi::class.java)
    }

    fun getProducts(search: String) : MutableLiveData<ProductResponse>{

        val productsData = MutableLiveData<ProductResponse>()

        productApi.getProductList(search).enqueue(object : Callback<ProductDTO>{

            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
                var exception = Exception(t)
                productsData.postValue(ProductResponse.Error(exception.localizedMessage, exception))
            }

            override fun onResponse(call: Call<ProductDTO>, DTO: Response<ProductDTO>) {
                var sizeList = DTO.body()!!.getProducts().size
                println(sizeList)
                if (DTO.isSuccessful && sizeList > 1) productsData.postValue(ProductResponse.Success(DTO.body()!!))
                else productsData.postValue(ProductResponse.Error("message", IllegalArgumentException()))
            }
        })
        return productsData
    }


}