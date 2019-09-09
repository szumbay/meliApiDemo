package com.example.meliapisdemo.model.productItem

import com.example.meliapisdemo.utils.ErrorType
import java.io.Serializable

sealed class DescriptionResponse : Serializable {

    data class Success(val description :Description) : DescriptionResponse()
    data class Error(val errorType: ErrorType) : DescriptionResponse()

}