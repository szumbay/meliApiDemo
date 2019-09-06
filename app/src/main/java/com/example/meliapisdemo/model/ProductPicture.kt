package com.example.meliapisdemo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductPicture(
@SerializedName("id") val id: String,
@SerializedName("url") val title: String,
@SerializedName("secure_url") val thumbnail: String
) : Serializable