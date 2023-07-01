package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class Payloads(

    @SerializedName("composite_fairing")
    val composite_fairing: CompositeFairing? = null,

    @SerializedName("option_1")
    val option_1: String? = null,

    @SerializedName("option_2")
    val option_2: String? = null
)