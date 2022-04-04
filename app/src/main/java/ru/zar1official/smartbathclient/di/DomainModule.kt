package ru.zar1official.smartbathclient.di

import org.koin.dsl.module
import ru.zar1official.smartbathclient.domain.usecases.DecreaseTemperatureUseCase
import ru.zar1official.smartbathclient.domain.usecases.IncreaseTemperatureUseCase
import ru.zar1official.smartbathclient.domain.usecases.ReadTemperatureUseCase

val domainModule = module {
    factory<DecreaseTemperatureUseCase> {
        DecreaseTemperatureUseCase(repository = get())
    }

    factory<IncreaseTemperatureUseCase> {
        IncreaseTemperatureUseCase(repository = get())
    }

    factory<ReadTemperatureUseCase> {
        ReadTemperatureUseCase(repository = get())
    }
}