package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class ThrustVacuum(

    @SerializedName("kN")
    val kN: Int? = null,

    @SerializedName("lbf")
    val lbf: Int? = null
)