package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class SecondStage(

    @SerializedName("burn_time_sec")
    val burn_time_sec: Int? = null,

    @SerializedName("engines")
    val engines: Int? = null,

    @SerializedName("fuel_amount_tons")
    val fuel_amount_tons: Double? = null,

    @SerializedName("payloads")
    val payloads: Payloads? = null,

    @SerializedName("thrust")
    val thrust: Thrust? = null
)