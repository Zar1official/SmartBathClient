package ru.zar1official.smartbathclient.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BathState(
    val uId: Long,
    val temp: Float,
    val craneActie: Boolean,
    val drainStatus: Boolean,
    val waterColor: Int,
    val fillingProcent: Float,
    val timeStamp: Long
)