package com.example.meliapisdemo.model

import java.lang.Exception

sealed class ProductResponse {

    data class Success(val productDTO: ProductDTO) : ProductResponse()
    data class Error(val message: String, val cause: Exception) : ProductResponse()
}
