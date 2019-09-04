package com.example.meliapisdemo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Product(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("price") val price: Double
) : Serializable