package com.example.meliapisdemo.model.productItem

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductPicture(
@SerializedName("id") val id: String,
@SerializedName("url") val picture: String,
@SerializedName("secure_url") val pictureS: String
) : Serializable