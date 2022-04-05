package ru.zar1official.smartbathclient.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import ru.zar1official.smartbathclient.data.models.BathState

class ServiceImpl(private val client: HttpClient) : Service {
    override suspend fun readBathState(uId: Long): BathState {
        return client.get {
            url {
                path("api", "getBoth")
                parameter("uId", uId)
            }
        }
    }

    override suspend fun fetchingWater(uId: Long, fetch: Boolean) {
        client.post<HttpResponse> {
            url {
                path("api", "SetBoth")
                formData {
                    parameter("uId", uId)
                    parameter("craneActive", fetch)
                }
            }
        }
    }

    override suspend fun changeWaterColor(uId: Long, color: Int) {
        client.post<HttpResponse> {
            url {
                path("api", "SetBoth")
                formData {
                    parameter("uId", uId)
                    parameter("color", color)
                }
            }
        }
    }

    override suspend fun changeDrainStatus(uId: Long, drain: Boolean) {
        client.post<HttpResponse> {
            url {
                path("api", "SetBoth")
                formData {
                    parameter("uId", uId)
                    parameter("drainStatus", drain)
                }
            }
        }
    }
}