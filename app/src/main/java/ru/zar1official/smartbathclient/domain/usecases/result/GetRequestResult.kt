package ru.zar1official.smartbathclient.domain.usecases.result

sealed class GetRequestResult<out T> {
    data class Success<T>(val data: T) : GetRequestResult<T>()
    object NetworkError : GetRequestResult<Nothing>()
}