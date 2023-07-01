package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class CompositeFairing(

    @SerializedName("diameter")
    val diameter: Diameter? = null,

    @SerializedName("height")
    val height: Height? = null
)