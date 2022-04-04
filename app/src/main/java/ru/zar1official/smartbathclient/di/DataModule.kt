package ru.zar1official.smartbathclient.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import org.koin.dsl.module
import ru.zar1official.smartbathclient.data.network.Constants
import ru.zar1official.smartbathclient.data.network.Service
import ru.zar1official.smartbathclient.data.network.ServiceImpl
import ru.zar1official.smartbathclient.data.repositories.RepositoryImpl
import ru.zar1official.smartbathclient.domain.repository.Repository
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

private fun provideKtorClient(): HttpClient {
    return HttpClient(CIO) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = Constants.BASE_HOST
                port = Constants.BASE_PORT
            }
        }
        engine {
            https {
                trustManager = object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

                    override fun getAcceptedIssuers(): Array<X509Certificate>? = null
                }
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
        }
    }
}

val dataModule = module {
    single<Repository> {
        return@single RepositoryImpl(service = get())
    }

    single<Service> {
        return@single ServiceImpl(client = get())
    }

    single<HttpClient> {
        provideKtorClient()
    }
}
