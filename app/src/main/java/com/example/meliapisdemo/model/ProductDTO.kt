package com.example.meliapisdemo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductDTO(@SerializedName("query") val query: String, results: List<Product>) : Serializable{

    @SerializedName("results") val result: List<Product> = results

    fun getProducts(): List<Product> {
        return result
    }

}