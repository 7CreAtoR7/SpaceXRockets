package ru.ilya.spacexrockets.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.ilya.spacexrockets.data.mapper.AppMapper
import ru.ilya.spacexrockets.data.remote.SpaceXApi
import ru.ilya.spacexrockets.domain.model.Rocket
import ru.ilya.spacexrockets.domain.repository.SpaceXRepository
import ru.ilya.spacexrockets.util.Resource
import java.io.IOException
import javax.inject.Inject

class SpaceXRepositoryImpl @Inject constructor(
    private val api: SpaceXApi,
    private val mapper: AppMapper
) : SpaceXRepository {

    override suspend fun getRockets(): Flow<Resource<List<Rocket>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val remoteRockets = api.getRockets()
                val modelRockets = mapper.mapListRocketDtoToListRocket(remoteRockets)
                emit(Resource.Success(modelRockets))
            } catch (e: IOException) {
                emit(Resource.Error(message = INTERNET_CONNECTION_ERROR))
            } catch (e: HttpException) {
                emit(Resource.Error(message = SOMETHING_WENT_WRONG))
            }
        }
    }

    companion object {

        private const val INTERNET_CONNECTION_ERROR = "Нет интернет соединения"
        private const val SOMETHING_WENT_WRONG = "Что-то пошло не так, повторите позже"
    }

}