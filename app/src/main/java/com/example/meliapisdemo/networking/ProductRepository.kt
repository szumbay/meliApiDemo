package com.example.meliapisdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.ProductDTO
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.utils.getErrorType
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
                var errorType = getErrorType(t)
                productsData.postValue(ProductResponse.Error(errorType))
            }

            override fun onResponse(call: Call<ProductDTO>, DTO: Response<ProductDTO>) {
                var sizeList = DTO.body()!!.getProducts().size
                if (DTO.isSuccessful && sizeList > 1) productsData.postValue(ProductResponse.Success(DTO.body()!!))
                    else if (sizeList == 0 ) productsData.postValue(ProductResponse.Error(ErrorType.CLIENT))
                else
                    productsData.postValue(ProductResponse.Error(getErrorType(DTO.code())))
            }
        })
        return productsData
    }


}