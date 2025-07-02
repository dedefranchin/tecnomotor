package com.desafio.tecnomotor.util

data class ResultWrapper<out T>(
    val success: Boolean,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T): ResultWrapper<T> {
            return ResultWrapper(true, data, null)
        }

        fun <T> error(msg: String, data: T? = null): ResultWrapper<T> {
            return ResultWrapper(false, data, msg)
        }
    }
} 