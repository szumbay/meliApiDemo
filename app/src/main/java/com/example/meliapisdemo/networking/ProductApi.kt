package com.example.meliapisdemo.networking

import com.example.meliapisdemo.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET("sites/MLA/search")
    fun getProductList( @Query("q" ) search: String): Call<ProductResponse>
}