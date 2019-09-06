package com.example.meliapisdemo.networking

import com.example.meliapisdemo.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("sites/MLA/search")
    fun getProductList( @Query("q" ) search: String): Call<ProductDTO>

    @GET("sites/MLA/autosuggest")
    fun getSuggestionList(@Query("q") suggestion: String): Call<SuggestionDTO>

    @GET("items/{id}")
    fun getProduct(@Path("id") id: String): Call<ProductItem>

    @GET("items/{id}/description")
    fun getProductDescription(@Path("id")id: String): Call<Description>
}