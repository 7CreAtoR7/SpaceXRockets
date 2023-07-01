package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class LandingLegs(

    @SerializedName("material")
    val material: String? = null,

    @SerializedName("number")
    val number: Int? = null
)