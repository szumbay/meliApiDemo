package com.example.meliapisdemo.utils

import java.io.IOException

private val SERVER_ERROR_CODE = 500

enum class ErrorType {

    /**
     * The [ErrorType.SERVER] is when the Failure was throw due to a fail in backend. So the request was made it successfully but the response arrived with a
     * 5xx status code
     */
    SERVER,

    /**
     * The [ErrorType.NETWORK] corresponds to a timeout or when the device does not have internet.
     */
    NETWORK,

    /**
     * Indicates when the user cancels the request or something interrupts the request.
     */
    CANCELED,

    /**
     * The [ErrorType.CLIENT] corresponds to request that was not properly performed. In these cases the request was made with some inconsistency and the
     * response arrived with a 4xx status code.
     */
    CLIENT,
    UNEXPECTED
}

fun getErrorType(errorCode: Int): ErrorType {
    return if (errorCode >= SERVER_ERROR_CODE) {
        ErrorType.SERVER
    } else {
        ErrorType.CLIENT
    }

}


fun getErrorType(t: Throwable): ErrorType {
    return if (t is IOException) {
        ErrorType.NETWORK
    } else {
        ErrorType.UNEXPECTED
    }
}