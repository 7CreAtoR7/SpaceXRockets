package ru.ilya.spacexrockets.data.remote.dto.rockets_dto

import com.google.gson.annotations.SerializedName

data class RocketDto(

    @SerializedName("active")
    val active: Boolean? = null,

    @SerializedName("boosters")
    val boosters: Int? = null,

    @SerializedName("company")
    val company: String? = null,

    @SerializedName("cost_per_launch")
    val cost_per_launch: Int? = null,

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("diameter")
    val diameter: Diameter? = null,

    @SerializedName("engines")
    val engines: Engines? = null,

    @SerializedName("first_flight")
    val first_flight: String? = null,

    @SerializedName("first_stage")
    val first_stage: FirstStage? = null,

    @SerializedName("height")
    val height: Height? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("landing_legs")
    val landing_legs: LandingLegs? = null,

    @SerializedName("mass")
    val mass: Mass? = null,

    @SerializedName("payload_weights")
    val payload_weights: List<PayloadWeight>? = null,

    @SerializedName("rocket_id")
    val rocket_id: String? = null,

    @SerializedName("rocket_name")
    val rocket_name: String? = null,

    @SerializedName("rocket_type")
    val rocket_type: String? = null,

    @SerializedName("second_stage")
    val second_stage: SecondStage? = null,

    @SerializedName("stages")
    val stages: Int? = null,

    @SerializedName("success_rate_pct")
    val success_rate_pct: Int? = null,

    @SerializedName("wikipedia")
    val wikipedia: String? = null,

    @SerializedName("flickr_images")
    val images: List<String>? = null
)