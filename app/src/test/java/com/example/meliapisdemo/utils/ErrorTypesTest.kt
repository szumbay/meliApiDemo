package com.example.meliapisdemo.utils

import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.utils.getErrorType
import org.junit.Assert
import org.junit.Test
import java.io.IOException
import java.lang.RuntimeException

class ErrorTypesTest {

    @Test
    fun whenNoInternetConnection_thrownNETWORK(){
        Assert.assertEquals(ErrorType.NETWORK,getErrorType(IOException()))
    }

    @Test
    fun whenSomethingBreaks_thrownUNEXPECTED(){
        Assert.assertEquals(ErrorType.UNEXPECTED, getErrorType(RuntimeException()))
    }

    @Test
    fun whenServerisDown_thrownSERVER(){
        Assert.assertEquals(ErrorType.SERVER, getErrorType(500))
    }

    @Test
    fun whenClientMakeBadRequest_thrownCLIENT(){
        Assert.assertEquals(ErrorType.CLIENT, getErrorType(404))
    }

}