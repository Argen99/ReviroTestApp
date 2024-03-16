package com.example.domain.core

sealed class Either<out R> {
    data class Success<out T>(val data: T) : Either<T>()
    data class Error(val exception: Exception) : Either<Nothing>()
}