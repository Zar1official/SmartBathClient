package ru.zar1official.smartbathclient.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import ru.zar1official.smartbathclient.data.models.BathState

class ServiceImpl(private val client: HttpClient) : Service {
    override suspend fun readBathState(uId: Int): BathState {
        return client.get {
            url {
                path("api", "getBoth")
                parameter("uId", uId)
            }
        }
    }
}