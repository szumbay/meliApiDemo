package com.example.meliapisdemo.model.productItem

import com.example.meliapisdemo.utils.ErrorType

sealed class ProductDetailResponse {

    data class Response(val description: DescriptionResponse, val productItem: ProductItemResponse) : ProductDetailResponse()
    data class Loading(val message: String): ProductDetailResponse()

}