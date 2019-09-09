package com.example.meliapisdemo.networking.api

import com.example.meliapisdemo.model.product.ProductDTO
import com.example.meliapisdemo.model.productItem.Description
import com.example.meliapisdemo.model.productItem.ProductItem
import com.example.meliapisdemo.model.suggestion.SuggestionDTO
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