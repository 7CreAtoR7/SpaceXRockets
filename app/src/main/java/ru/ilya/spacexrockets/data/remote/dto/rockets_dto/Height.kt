package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class Height(

    @SerializedName("feet")
    val feet: Double? = null,

    @SerializedName("meters")
    val meters: Double? = null
)