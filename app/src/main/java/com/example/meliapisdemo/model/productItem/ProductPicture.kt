package com.example.meliapisdemo.model.productItem

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductPicture(
@SerializedName("id") val id: String,
@SerializedName("url") val picture: String,
@SerializedName("secure_url") val pictureS: String,
@SerializedName("size") val size : String,
@SerializedName("max_size") val maxSize : String
) : Serializable {

    fun getDensityPixel(density:Int) : List<Int>{
        val sizes = this.getSize()
        val width = sizes[0] * density
        val height = sizes[1] * density
        return mutableListOf(width,height)
    }

    fun getSize() : List<Int>{
        val sizeString = this.size
        var digit = true
        var width = ""
        var height = ""

        for (i in sizeString.indices) {
            if (sizeString[i].isDigit() && digit) width += sizeString[i]
            else if (sizeString[i].isDigit()) height += sizeString[i]
            else digit = false
        }

        return mutableListOf(width.toInt(),height.toInt())
    }



}