package ru.zar1official.smartbathclient.di

import org.koin.dsl.module
import ru.zar1official.smartbathclient.domain.usecases.*

val domainModule = module {
    factory<DecreaseTemperatureUseCase> {
        DecreaseTemperatureUseCase(repository = get())
    }

    factory<IncreaseTemperatureUseCase> {
        IncreaseTemperatureUseCase(repository = get())
    }

    factory<ReadBathStateUseCase> {
        ReadBathStateUseCase(repository = get())
    }

    factory<ReadUIdUseCase> {
        ReadUIdUseCase(repository = get())
    }

    factory<StartFetchingWaterUseCase> {
        StartFetchingWaterUseCase(repository = get())
    }

    factory<StopFetchingWaterUseCase> {
        StopFetchingWaterUseCase(repository = get())
    }

    factory<ChangeWaterColorUseCase> {
        ChangeWaterColorUseCase(repository = get())
    }

    factory<OpenDrainUseCase> {
        OpenDrainUseCase(repository = get())
    }

    factory<CloseDrainUseCase> {
        CloseDrainUseCase(repository = get())
    }
}