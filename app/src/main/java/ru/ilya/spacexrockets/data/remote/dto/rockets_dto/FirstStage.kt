package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class FirstStage(

    @SerializedName("burn_time_sec")
    val burn_time_sec: Int? = null,

    @SerializedName("cores")
    val cores: Int? = null,

    @SerializedName("engines")
    val engines: Int? = null,

    @SerializedName("fuel_amount_tons")
    val fuel_amount_tons: Double? = null,

    @SerializedName("reusable")
    val reusable: Boolean? = null,

    @SerializedName("thrust_sea_level")
    val thrust_sea_level: ThrustSeaLevel? = null,

    @SerializedName("thrust_vacuum")
    val thrust_vacuum: ThrustVacuum? = null
)