package com.example.meliapisdemo.networking

import com.example.meliapisdemo.model.ProductResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ProductRepositoryTest {



    @Before
    fun setUp(){}


    @Test
    fun whenSearchIsCompletedWithSuccess_returnProductResponseWithValidDTO(){
        val mockRetrofitService = Mockito.spy(RetrofitService())
        Mockito.`when`(mockRetrofitService .createService(ProductApi::class.java)).thenReturn(ApiProductMock(true,true))

        ProductRepository.productApi = mockRetrofitService.createService(ProductApi::class.java)
        val productResponseLiveData = ProductRepository.getProducts("iphone")

        Assert.assertEquals(ProductResponse.Success::class.java, productResponseLiveData.value)
    }

}