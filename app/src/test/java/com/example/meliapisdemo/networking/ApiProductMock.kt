package com.example.meliapisdemo.networking

import com.example.meliapisdemo.model.product.Product
import com.example.meliapisdemo.model.product.ProductDTO
import com.example.meliapisdemo.model.productItem.Description
import com.example.meliapisdemo.model.productItem.ProductItem
import com.example.meliapisdemo.model.suggestion.SuggestionDTO
import com.example.meliapisdemo.networking.api.ProductApi
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ApiProductMock(private val success: Boolean,
                     private val validRequest: Boolean) : ProductApi {
    override fun getProduct(id: String): Call<ProductItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProductDescription(id: String): Call<Description> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSuggestionList(suggestion: String): Call<SuggestionDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getProductList(search: String): Call<ProductDTO> {
        return getResponseCall()
    }

    fun getResponseCall(): Call<ProductDTO>{
        return object : Call<ProductDTO>{

            override fun enqueue(callback: Callback<ProductDTO>) {
                when {
                    success && validRequest -> callback.onResponse(this, Response.success(getProductDTO(true)))
                    success && !validRequest -> callback.onResponse(this, Response.success(getProductDTO(false)))
                    !success && !validRequest -> callback.onFailure(this, IOException())
                    !success && validRequest -> callback.onResponse(this, Response.error(500, ResponseBody.create(
                                MediaType.parse("application/json"),
                                "{\"key\":[\"somestuff\"]}"
                            )))
                }
            }

            override fun isExecuted(): Boolean {
                return true
            }

            override fun clone(): Call<ProductDTO> {
                return this
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun cancel() {
            }

            override fun execute(): Response<ProductDTO> {
                return Response.success(getProductDTO(true))
            }

            override fun request(): Request {
                return Response.success(getProductDTO(true)).raw().request()
            }
        }
    }
    fun getProductDTO(isSuccess:Boolean) : ProductDTO {
        val successList =  ArrayList<Product>()
        successList.add(Product("test", "test", "test", 2.3))
        if (isSuccess) return ProductDTO("iphone", successList)
        else return ProductDTO("iphone", ArrayList<Product>())
    }


}