package com.example.countrylist.data

sealed class Results<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T> : Results<T>()
    class Success<T>(data: T) : Results<T>(data = data)
    class Error<T>(throwable: Throwable?) : Results<T>(throwable = throwable)
}