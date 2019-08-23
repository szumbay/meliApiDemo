package com.example.meliapisdemo.model

import com.google.gson.annotations.SerializedName

class ProductResponse {

    @SerializedName("query") val query: String
    @SerializedName("results") val result: List<Product>

    constructor(query: String, results: List<Product>){
        this.query = query
        this.result = results
    }

    fun getProducts(): List<Product> {
        return result
    }

}