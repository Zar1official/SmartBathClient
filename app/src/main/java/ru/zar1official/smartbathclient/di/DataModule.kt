package ru.zar1official.smartbathclient.di

import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.zar1official.smartbathclient.data.network.Constants.BASE_URL
import ru.zar1official.smartbathclient.data.network.Service
import ru.zar1official.smartbathclient.data.repositories.RepositoryImpl
import ru.zar1official.smartbathclient.domain.repository.Repository

private fun provideRetrofitClient(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        ).build()
}

private fun provideRetrofitService(retrofit: Retrofit): Service {
    return retrofit.create(Service::class.java)
}

val dataModule = module {
    single<Repository> {
        return@single RepositoryImpl()
    }

    single<Service> {
        provideRetrofitService(retrofit = get())
    }

    single<Retrofit> {
        provideRetrofitClient()
    }
}