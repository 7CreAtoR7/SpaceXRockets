package ru.ilya.spacexrockets.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ilya.spacexrockets.domain.model.Rocket
import ru.ilya.spacexrockets.util.Resource

interface SpaceXRepository {

    suspend fun getRockets(): Flow<Resource<List<Rocket>>>
}