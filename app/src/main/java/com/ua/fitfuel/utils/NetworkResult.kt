package com.ua.fitfuel.utils

import retrofit2.Response

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()

    companion object {
        fun <T> toNetworkResult(response: Response<T>): NetworkResult<T> {
            return when {
                response.message().toString().contains("timeout") -> Error("Timeout")
                response.code() == 401 -> Error("Invalid API Key")
                response.code() == 402 -> Error("API Key Limited")
                response.body() == null -> Error("Recipes not found")
                response.isSuccessful -> Success(response.body()!!)
                else -> Error(response.message().toString())
            }
        }
    }
}