package ru.ilya.spacexrockets.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rocket(
    val id: Int,
    val rocketId: String,
    val rocketName: String,
    val imageUrl: String,

    val heightFeet: Double,
    val diameterFeet: Double,
    val massLb: Int,
    val LeoPayload: Int,

    val firstFlight: String,
    val country: String,
    val costPerLaunch: Int,

    val enginesCountFirstStage: Int,
    val fuelAmountTonsFirstStage: Double,
    val burnTimeSecFirstStage: Int,

    val enginesCountSecondStage: Int,
    val fuelAmountTonsSecondStage: Double,
    val burnTimeSecSecondStage: Int,
) : Parcelable