package com.example.meliapisdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.ProductDTO
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.utils.getErrorType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductRepository {

    var productApi = RetrofitService().createService(ProductApi::class.java)

    fun getProducts(search: String, productLiveData: MutableLiveData<ProductResponse>){

        productApi.getProductList(search).enqueue(object : Callback<ProductDTO>{

            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
                val errorType = getErrorType(t)
                productLiveData.postValue(ProductResponse.Error(errorType))
            }

            override fun onResponse(call: Call<ProductDTO>, DTO: Response<ProductDTO>) {
                if (DTO.isSuccessful && DTO.body()!!.getProducts().isNotEmpty()){
                    productLiveData.postValue(ProductResponse.Success(DTO.body()!!))
                }
                else if (DTO.isSuccessful && DTO.body()!!.getProducts().isEmpty()) {
                    productLiveData.postValue(ProductResponse.Error(ErrorType.CLIENT))
                }
                else
                    productLiveData.postValue(ProductResponse.Error(getErrorType(DTO.code())))
            }
        })
    }


}