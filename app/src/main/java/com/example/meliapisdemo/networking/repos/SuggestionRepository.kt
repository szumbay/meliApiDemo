
package com.example.meliapisdemo.networking.repos

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.suggestion.SuggestionDTO
import com.example.meliapisdemo.networking.api.ProductApi
import com.example.meliapisdemo.networking.api.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SuggestionRepository {
    var productApi = RetrofitService()
        .createService(ProductApi::class.java)

    var callFrom : Call<SuggestionDTO>? = null

    fun cancelCall(){
        callFrom?.apply {
            if (!this.isExecuted) this.cancel()
        }
    }

    fun getSuggestion(search: String, suggestionsLiveData: MutableLiveData<SuggestionDTO>){

        productApi.getSuggestionList(search).enqueue(object : Callback<SuggestionDTO> {

            override fun onFailure(call: Call<SuggestionDTO>, t: Throwable) {

            }

            override fun onResponse(call: Call<SuggestionDTO>, DTO: Response<SuggestionDTO>) {
                if (DTO.isSuccessful)suggestionsLiveData.postValue(DTO.body())
            }
        })
    }
}