package ru.ilya.spacexrockets.data.remote

import retrofit2.http.GET
import ru.ilya.spacexrockets.data.remote.dto.rockets_dto.RocketDto

interface SpaceXApi {

    @GET("rockets")
    suspend fun getRockets(): List<RocketDto>
}