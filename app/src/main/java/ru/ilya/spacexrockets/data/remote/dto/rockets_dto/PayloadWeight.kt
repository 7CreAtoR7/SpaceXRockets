package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class PayloadWeight(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("kg")
    val kg: Int? = null,

    @SerializedName("lb")
    val lb: Int? = null,

    @SerializedName("name")
    val name: String? = null
)