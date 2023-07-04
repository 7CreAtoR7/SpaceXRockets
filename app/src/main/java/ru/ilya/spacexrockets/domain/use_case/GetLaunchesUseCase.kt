package ru.ilya.spacexrockets.domain.use_case

import kotlinx.coroutines.flow.Flow
import ru.ilya.spacexrockets.domain.model.launches_model.Launch
import ru.ilya.spacexrockets.domain.repository.SpaceXRepository
import ru.ilya.spacexrockets.util.Resource
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(
    private val spaceXRepository: SpaceXRepository
) {

    suspend operator fun invoke(rocketName: String): Flow<Resource<List<Launch>>> {
        return spaceXRepository.getLaunchesByRocketName(rocketName = rocketName)
    }
}