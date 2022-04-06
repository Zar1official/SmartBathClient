package ru.zar1official.smartbathclient.domain.usecases.result

sealed class PostRequestResult {
    object Success : PostRequestResult()
    object Error : PostRequestResult()
}
