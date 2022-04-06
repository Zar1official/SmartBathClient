package ru.zar1official.smartbathclient.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.zar1official.smartbathclient.presentation.main_screen.MainViewModel

val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel(
            readBathStateUseCase = get(),
            readUIdUseCase = get(),
            startFetchingWaterUseCase = get(),
            stopFetchingWaterUseCase = get(),
            changeWaterColorUseCase = get(),
            closeDrainUseCase = get(),
            openDrainUseCase = get(),
            changeTemperatureUseCase = get()
        )
    }
}