package com.example.meliapisdemo.model

import com.example.meliapisdemo.utils.ErrorType

sealed class ProductResponse {

    data class Success(val productDTO: ProductDTO) : ProductResponse()
    data class Error(val cause: ErrorType) : ProductResponse()

}
