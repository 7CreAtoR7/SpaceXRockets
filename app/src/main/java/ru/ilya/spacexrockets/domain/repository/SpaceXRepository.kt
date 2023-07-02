package ru.ilya.spacexrockets.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ilya.spacexrockets.domain.model.launches_model.Launch
import ru.ilya.spacexrockets.domain.model.rockets_model.Rocket
import ru.ilya.spacexrockets.util.Resource

interface SpaceXRepository {

    suspend fun getRockets(): Flow<Resource<List<Rocket>>>

    suspend fun getLaunchesByRocketId(rocketName: String): Flow<Resource<List<Launch>>>
}