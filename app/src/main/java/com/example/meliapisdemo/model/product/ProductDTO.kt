package com.example.meliapisdemo.model.product

import com.example.meliapisdemo.model.product.Product
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductDTO(@SerializedName("query") val query: String, @SerializedName("results") val results: List<Product>) : Serializable{

    fun getProducts(): List<Product> {
        return results
    }

}