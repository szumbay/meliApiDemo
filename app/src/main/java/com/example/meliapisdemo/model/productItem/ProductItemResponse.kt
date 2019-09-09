package com.example.meliapisdemo.model.productItem

import com.example.meliapisdemo.utils.ErrorType
import java.io.Serializable

sealed class ProductItemResponse : Serializable{

    data class Success(val productItem : ProductItem) : ProductItemResponse()
    data class Error(val errorType: ErrorType) : ProductItemResponse()

}