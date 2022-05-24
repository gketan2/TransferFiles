package com.k10.transferfiles.utils

class ResultWrapper<T>(val status: ResultStatus, val data: T?, val message: String) {
    companion object {
        fun <T> loading(message: String = ""): ResultWrapper<T> {
            return ResultWrapper(ResultStatus.LOADING, null, message)
        }

        fun <T> success(data: T, message: String = ""): ResultWrapper<T> {
            return ResultWrapper(ResultStatus.SUCCESS, data, message)
        }

        fun <T> failed(message: String): ResultWrapper<T> {
            return ResultWrapper(ResultStatus.FAILED, null, message)
        }
    }
}

enum class ResultStatus {
    LOADING, SUCCESS, FAILED
}