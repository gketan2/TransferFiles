package com.k10.transferfiles.utils

class ResultWrapper<T>(val status: ResultStatus, val data: T?, val message: String) {
    companion object {
        fun <T> loading(data: T?, message: String = ""): ResultWrapper<T> {
            return ResultWrapper(ResultStatus.LOADING, data, message)
        }

        fun <T> success(data: T, message: String = ""): ResultWrapper<T> {
            return ResultWrapper(ResultStatus.SUCCESS, data, message)
        }

        fun <T> failed(data: T?, message: String): ResultWrapper<T> {
            return ResultWrapper(ResultStatus.FAILED, data, message)
        }
    }
}

enum class ResultStatus {
    LOADING, SUCCESS, FAILED
}