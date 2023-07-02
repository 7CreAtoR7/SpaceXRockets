package ru.ilya.spacexrockets.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ilya.spacexrockets.data.remote.dto.launches_dto.LaunchDto
import ru.ilya.spacexrockets.data.remote.dto.rockets_dto.RocketDto

interface SpaceXApi {

    @GET("rockets")
    suspend fun getRockets(): List<RocketDto>

    @GET("launches")
    suspend fun getLaunchesByRocketId(@Query("rocket_name") rocketName: String): List<LaunchDto>
}