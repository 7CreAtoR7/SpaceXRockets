package ru.ilya.spacexrockets.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launchesTable")
data class LaunchEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "rocket_name")
    val rocketName: String,

    @ColumnInfo(name = "mission_name")
    val missionName: String,

    @ColumnInfo(name = "launch_success")
    val launchSuccess: Boolean,

    @ColumnInfo(name = "launch_date_unix")
    val launchDateUnix: Int,
)