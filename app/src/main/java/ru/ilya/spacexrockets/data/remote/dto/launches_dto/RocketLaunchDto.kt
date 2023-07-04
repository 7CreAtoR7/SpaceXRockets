package ru.ilya.spacexrockets.data.remote.dto.launches_dto

import com.google.gson.annotations.SerializedName

data class RocketLaunchDto(

    @SerializedName("rocket_id")
    val rocket_id: String,

    @SerializedName("rocket_name")
    val rocket_name: String,

    @SerializedName("rocket_type")
    val rocket_type: String,
)