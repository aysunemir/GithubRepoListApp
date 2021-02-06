package com.aemir.githubrepolist.api

// Reference: https://developer.android.com/jetpack/guide#addendum

sealed class Resource<out T>

object Loading : Resource<Nothing>()
data class Success<T>(val data: T) : Resource<T>()
data class Error(val message: String) : Resource<Nothing>()
