package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class Engines(

    @SerializedName("engine_loss_max")
    val engine_loss_max: Int? = null,

    @SerializedName("layout")
    val layout: String? = null,

    @SerializedName("number")
    val number: Int? = null,

    @SerializedName("propellant_1")
    val propellant_1: String? = null,

    @SerializedName("propellant_2")
    val propellant_2: String? = null,

    @SerializedName("thrust_sea_level")
    val thrust_sea_level: ThrustSeaLevel? = null,

    @SerializedName("thrust_to_weight")
    val thrust_to_weight: Double? = null,

    @SerializedName("thrust_vacuum")
    val thrust_vacuum: ThrustVacuum? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("version")
    val version: String? = null
)