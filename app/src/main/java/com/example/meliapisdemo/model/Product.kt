package com.example.meliapisdemo.model

import com.google.gson.annotations.SerializedName

class Product{

    @SerializedName ("id") val id: String
    @SerializedName("title") val title: String
    @SerializedName("thumbnail") val thumbnail: String
    @SerializedName("price") val price: Double

    constructor(id: String, title: String, thumbnail: String, price: Double) {
        this.id = id
        this.title = title
        this.thumbnail = thumbnail
        this.price = price
    }


}