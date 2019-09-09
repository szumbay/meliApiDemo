package com.example.meliapisdemo.networking.repos

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.productItem.Description
import com.example.meliapisdemo.model.productItem.DescriptionResponse
import com.example.meliapisdemo.networking.api.ProductApi
import com.example.meliapisdemo.networking.api.RetrofitService
import com.example.meliapisdemo.utils.getErrorType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DescriptionRepository {

    var productApi = RetrofitService()
        .createService(ProductApi::class.java)

    fun getProductDescription(id: String, liveData: MutableLiveData<DescriptionResponse>){

        productApi.getProductDescription(id).enqueue(object : Callback<Description>{
            override fun onFailure(call: Call<Description>, t: Throwable) {
                val error = getErrorType(t)
                liveData.postValue(DescriptionResponse.Error(error))
            }

            override fun onResponse(call: Call<Description>, response: Response<Description>) {
                if (response.isSuccessful)liveData.postValue(DescriptionResponse.Success(response.body()!!))
            }

        })
    }
}