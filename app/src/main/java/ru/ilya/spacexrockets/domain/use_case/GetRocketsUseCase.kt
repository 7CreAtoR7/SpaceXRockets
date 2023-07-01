package ru.ilya.spacexrockets.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ilya.spacexrockets.domain.model.Rocket
import ru.ilya.spacexrockets.domain.repository.SpaceXRepository
import ru.ilya.spacexrockets.util.Resource
import javax.inject.Inject

class GetRocketsUseCase @Inject constructor(
    private val spaceXRepository: SpaceXRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Rocket>>> {
        return spaceXRepository.getRockets()
    }
}