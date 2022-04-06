package ru.zar1official.smartbathclient.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import ru.zar1official.smartbathclient.data.models.BathState

class ServiceImpl(private val client: HttpClient) : Service {
    private val setPath = arrayOf("api", "SetBoth")
    private val getPath = arrayOf("api", "getBoth")

    override suspend fun readBathState(uId: Long): BathState {
        return client.get {
            url {
                path(*getPath)
                parameter("uId", uId)
            }
        }
    }

    override suspend fun fetchingWater(uId: Long, fetch: Boolean) {
        client.post<HttpResponse> {
            url {
                path(*setPath)
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
                path(*setPath)
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
                path(*setPath)
                formData {
                    parameter("uId", uId)
                    parameter("drainStatus", drain)
                }
            }
        }
    }

    override suspend fun changeTemperatureUseCase(uId: Long, temperature: Float) {
        client.post<HttpResponse> {
            url {
                path(*setPath)
                formData {
                    parameter("uId", uId)
                    parameter("temp", temperature)
                }
            }
        }
    }
}