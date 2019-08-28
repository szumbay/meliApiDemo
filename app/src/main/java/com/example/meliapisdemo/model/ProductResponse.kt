package com.example.meliapisdemo.model

import com.example.meliapisdemo.utils.ErrorType
import java.io.Serializable

sealed class ProductResponse : Serializable{

    data class Success(val productDTO: ProductDTO) : ProductResponse()
    data class Error(val cause: ErrorType) : ProductResponse()

}
