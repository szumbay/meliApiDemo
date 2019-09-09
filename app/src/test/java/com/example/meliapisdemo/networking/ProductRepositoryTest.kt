package com.example.meliapisdemo.networking

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.meliapisdemo.model.product.ProductResponse
import com.example.meliapisdemo.networking.api.ProductApi
import com.example.meliapisdemo.networking.api.RetrofitService
import com.example.meliapisdemo.networking.repos.ProductRepository
import com.example.meliapisdemo.utils.ErrorType
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class ProductRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Test
    fun whenSearchIsCompletedWithSuccess_returnProductResponseSuccess(){
        val mockRetrofitService = Mockito.spy(RetrofitService())
        Mockito.`when`(mockRetrofitService .createService(ProductApi::class.java)).thenReturn(ApiProductMock(true,true))

        ProductRepository.productApi = mockRetrofitService.createService(ProductApi::class.java)

        val productResponseLiveData = MutableLiveData<ProductResponse>()
        ProductRepository.getProducts("iphone",productResponseLiveData)

        Assert.assertEquals(ProductResponse.Success::class.java, productResponseLiveData.value!!::class.java)
    }

    @Test
    fun whenSearchReturnInvalidResponse_returnProductResponseWithClientError(){
        val mockRetrofitService = Mockito.spy(RetrofitService())
        Mockito.`when`(mockRetrofitService .createService(ProductApi::class.java)).thenReturn(ApiProductMock(true,false))

        ProductRepository.productApi = mockRetrofitService.createService(ProductApi::class.java)
        val productResponseLiveData = MutableLiveData<ProductResponse>()
        ProductRepository.getProducts("iphone",productResponseLiveData)
        var errorType = ErrorType.CANCELED
        productResponseLiveData.value?.apply {
            when(this){
                is ProductResponse.Error ->  errorType = this.cause
            }
        }
        Assert.assertEquals(ErrorType.CLIENT, errorType)
    }

    @Test
    fun whenNoInternet_returnProductResponseWithNetworkError(){
        val mockRetrofitService = Mockito.spy(RetrofitService())
        Mockito.`when`(mockRetrofitService .createService(ProductApi::class.java)).thenReturn(ApiProductMock(false,false))

        ProductRepository.productApi = mockRetrofitService.createService(ProductApi::class.java)
        val productResponseLiveData = MutableLiveData<ProductResponse>()
        ProductRepository.getProducts("iphone",productResponseLiveData)
        var errorType = ErrorType.CANCELED
        productResponseLiveData.value?.apply {
            when(this){
                is ProductResponse.Error ->  errorType = this.cause
            }
        }
        Assert.assertEquals(ErrorType.NETWORK, errorType)
    }
    @Test
    fun whenServerIsDown_returnProductResponseWithServerError(){
        val mockRetrofitService = Mockito.spy(RetrofitService())
        Mockito.`when`(mockRetrofitService .createService(ProductApi::class.java)).thenReturn(ApiProductMock(false,true))

        ProductRepository.productApi = mockRetrofitService.createService(ProductApi::class.java)
        val productResponseLiveData = MutableLiveData<ProductResponse>()
        ProductRepository.getProducts("iphone",productResponseLiveData)
        var errorType = ErrorType.CANCELED
        productResponseLiveData.value?.apply {
            when(this){
                is ProductResponse.Error ->  errorType = this.cause
            }
        }
        Assert.assertEquals(ErrorType.SERVER, errorType)
    }



}