package com.example.meliapisdemo.networking

import com.example.meliapisdemo.model.Product
import com.example.meliapisdemo.model.ProductDTO
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiProductMock(private val success: Boolean,
                     private val validRequest: Boolean) : ProductApi {


    override fun getProductList(search: String): Call<ProductDTO> {
        return getResponseCall()
    }

    fun getResponseCall(): Call<ProductDTO>{
        return object : Call<ProductDTO>{

            override fun enqueue(callback: Callback<ProductDTO>) {
                when {
                    success && validRequest -> callback.onResponse(this, Response.success(getProductDTO(true)))
                    success && !validRequest -> callback.onResponse(this, Response.success(getProductDTO(false)))
                    !success && !validRequest -> callback.onFailure(this, Throwable())
                    !success -> callback.onFailure(this, Throwable())
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
    fun getProductDTO(isSuccess:Boolean) : ProductDTO{
        val successList =  ArrayList<Product>()
        successList.add(Product("test","test","test",2.3))
        if (isSuccess) return ProductDTO("iphone",successList)
        else return ProductDTO("iphone", ArrayList<Product>())
    }


}