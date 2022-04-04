package ru.zar1official.smartbathclient.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.zar1official.smartbathclient.presentation.MainViewModel

val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel(
            increaseTemperatureUseCase = get(),
            decreaseTemperatureUseCase = get(),
            readTemperatureUseCase = get(),
            readBathStateUseCase = get(),
            readUIdUseCase = get(),
            startFetchingWaterUseCase = get(),
            stopFetchingWaterUseCase = get()
        )
    }
}