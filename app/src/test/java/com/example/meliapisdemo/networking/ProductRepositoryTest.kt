package com.example.meliapisdemo.networking

import com.example.meliapisdemo.model.ProductResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ProductRepositoryTest {

    private lateinit var mockRetrofitService: RetrofitService


    @Before
    fun setUp(){

    }


    @Test
    fun whenSearchIsCompletedWithSuccess_returnProductResponsewithValidDTO(){
        mockRetrofitService = Mockito.mock(RetrofitService::class.java)
        Mockito.`when`(mockRetrofitService .createService(ProductApi::class.java)).thenReturn(ApiProductMock(true,true))
        ProductRepository.productApi = mockRetrofitService.createService(ProductApi::class.java)
        val productResponseLiveData = ProductRepository.getProducts("iphone")
        Assert.assertEquals(ProductResponse.Success::class.java, productResponseLiveData.value)
    }

}