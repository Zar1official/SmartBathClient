package ru.zar1official.smartbathclient.presentation.main_screen

sealed class MainScreenEvent {
    object LoadingError : MainScreenEvent()
    object Error : MainScreenEvent()
}