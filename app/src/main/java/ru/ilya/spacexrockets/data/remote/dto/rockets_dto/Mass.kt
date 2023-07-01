package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class Mass(

    @SerializedName("kg")
    val kg: Int? = null,

    @SerializedName("lb")
    val lb: Int? = null
)