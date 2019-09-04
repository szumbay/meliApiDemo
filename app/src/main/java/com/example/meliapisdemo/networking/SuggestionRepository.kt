package com.example.meliapisdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.SuggestionDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SuggestionRepository {
    var productApi = RetrofitService().createService(ProductApi::class.java)

    lateinit var callFrom : Call<SuggestionDTO>

    fun cancelCall(){
        if(!callFrom.isExecuted){
            callFrom.cancel()
        }
    }

    fun getSuggestion(search: String, suggestionsLiveData: MutableLiveData<SuggestionDTO>){

        productApi.getSuggestionList(search).enqueue(object : Callback<SuggestionDTO> {

            override fun onFailure(call: Call<SuggestionDTO>, t: Throwable) {
                callFrom = call
            }

            override fun onResponse(call: Call<SuggestionDTO>, DTO: Response<SuggestionDTO>) {
                callFrom = call
                if (DTO.isSuccessful)suggestionsLiveData.postValue(DTO.body())
            }
        })
    }
}