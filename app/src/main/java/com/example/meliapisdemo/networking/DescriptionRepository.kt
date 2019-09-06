package com.example.meliapisdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.Description
import com.example.meliapisdemo.model.SuggestionDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DescriptionRepository {

    var productApi = RetrofitService().createService(ProductApi::class.java)

    fun getProductDescription(id: String, liveData: MutableLiveData<Description>){

        productApi.getProductDescription(id).enqueue(object : Callback<Description>{
            override fun onFailure(call: Call<Description>, t: Throwable) {
            }

            override fun onResponse(call: Call<Description>, response: Response<Description>) {
                if (response.isSuccessful)liveData.postValue(response.body())
            }

        })
    }
}