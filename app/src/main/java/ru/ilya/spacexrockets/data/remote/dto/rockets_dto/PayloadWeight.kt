package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class PayloadWeight(

    @SerializedName("id")
    val id: String,

    @SerializedName("kg")
    val kg: Int,

    @SerializedName("lb")
    val lb: Int,

    @SerializedName("name")
    val name: String
)