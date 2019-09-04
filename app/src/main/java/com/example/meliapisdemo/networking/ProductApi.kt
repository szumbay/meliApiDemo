package com.example.meliapisdemo.networking

import com.example.meliapisdemo.model.ProductDTO
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.model.SuggestionDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET("sites/MLA/search")
    fun getProductList( @Query("q" ) search: String): Call<ProductDTO>

    @GET("sites/MLA/autosuggest")
    fun getSuggestionList(@Query("q") suggestion: String): Call<SuggestionDTO>
}