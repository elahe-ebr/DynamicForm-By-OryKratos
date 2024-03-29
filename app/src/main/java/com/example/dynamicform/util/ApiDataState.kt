package com.example.dynamicform.util

sealed class ApiDataState<out R> {
    data object Loading : ApiDataState<Nothing>()
    data class Success<out T>(val data: T) : ApiDataState<T>()
    data class Error(val e: Throwable) : ApiDataState<Nothing>()
}
