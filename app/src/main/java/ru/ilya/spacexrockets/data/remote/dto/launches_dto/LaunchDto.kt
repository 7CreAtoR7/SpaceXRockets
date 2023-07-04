package ru.ilya.spacexrockets.data.remote.dto.launches_dto

import com.google.gson.annotations.SerializedName

data class LaunchDto(

    @SerializedName("mission_id")
    val mission_id: List<Any>? = null,

    @SerializedName("mission_name")
    val mission_name: String? = null,

    @SerializedName("launch_success")
    val launch_success: Boolean? = null,

    @SerializedName("rocket")
    val rocket: RocketLaunchDto? = null,

    @SerializedName("launch_date_unix")
    val launch_date_unix: Int? = null,

    @SerializedName("crew")
    val crew: Any? = null,

    @SerializedName("details")
    val details: String? = null,

    @SerializedName("flight_number")
    val flight_number: Int? = null,

    @SerializedName("is_tentative")
    val is_tentative: Boolean? = null,

    @SerializedName("launch_date_local")
    val launch_date_local: String? = null,

    @SerializedName("launch_date_utc")
    val launch_date_utc: String? = null,

    @SerializedName("launch_window")
    val launch_window: Int? = null,

    @SerializedName("launch_year")
    val launch_year: String? = null,

    @SerializedName("ships")
    val ships: List<Any>? = null,

    @SerializedName("static_fire_date_unix")
    val static_fire_date_unix: Int? = null,

    @SerializedName("static_fire_date_utc")
    val static_fire_date_utc: String? = null,

    @SerializedName("tbd")
    val tbd: Boolean? = null,

    @SerializedName("tentative_max_precision")
    val tentative_max_precision: String? = null,

    @SerializedName("upcoming")
    val upcoming: Boolean? = null
)