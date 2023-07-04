package ru.ilya.spacexrockets.domain.model.launches_model

data class Launch(
    val missionName: String,
    val launchSuccess: Boolean,
    val launchDateUnix: Int,
    val rocketName: String
)