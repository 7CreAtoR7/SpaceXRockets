package ru.ilya.spacexrockets.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocketsTable")
data class RocketEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "rocket_id")
    val rocketId: String,

    @ColumnInfo(name = "rocket_name")
    val rocketName: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "height_m")
    val heightM: Double,

    @ColumnInfo(name = "height_feet")
    val heightFeet: Double,

    @ColumnInfo(name = "diameter_m")
    val diameterM: Double,

    @ColumnInfo(name = "diameter_feet")
    val diameterFeet: Double,

    @ColumnInfo(name = "mass_kg")
    val massKg: Int,

    @ColumnInfo(name = "mass_lb")
    val massLb: Int,

    @ColumnInfo(name = "leo_payload_kg")
    val LeoPayloadKg: Int,

    @ColumnInfo(name = "leo_payload_lb")
    val LeoPayloadLb: Int,

    @ColumnInfo(name = "first_flight")
    val firstFlight: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "cost_per_launch")
    val costPerLaunch: Int,

    @ColumnInfo(name = "engines_count_first_stage")
    val enginesCountFirstStage: Int,

    @ColumnInfo(name = "fuel_amount_tons_first_stage")
    val fuelAmountTonsFirstStage: Double,

    @ColumnInfo(name = "burn_time_sec_first_stage")
    val burnTimeSecFirstStage: Int,

    @ColumnInfo(name = "engines_count_second_stage")
    val enginesCountSecondStage: Int,

    @ColumnInfo(name = "fuel_amount_tons_second_stage")
    val fuelAmountTonsSecondStage: Double,

    @ColumnInfo(name = "burn_time_sec_second_stage")
    val burnTimeSecSecondStage: Int,
)