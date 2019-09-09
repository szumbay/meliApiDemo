package com.example.meliapisdemo.model.productItem

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("pictures") val pictures: List<ProductPicture>,
    @SerializedName("price") val price: Double
) : Serializable