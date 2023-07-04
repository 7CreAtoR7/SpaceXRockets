package ru.ilya.spacexrockets.data.repository

import android.util.Log
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.ilya.spacexrockets.data.local.AppDatabase
import ru.ilya.spacexrockets.data.mapper.AppMapper
import ru.ilya.spacexrockets.data.remote.SpaceXApi
import ru.ilya.spacexrockets.domain.model.launches_model.Launch
import ru.ilya.spacexrockets.domain.model.rockets_model.Rocket
import ru.ilya.spacexrockets.domain.repository.SpaceXRepository
import ru.ilya.spacexrockets.util.Resource
import java.io.IOException
import javax.inject.Inject

class SpaceXRepositoryImpl @Inject constructor(
    private val appDb: AppDatabase,
    private val api: SpaceXApi,
    private val mapper: AppMapper
) : SpaceXRepository {

    override suspend fun getRockets(): Flow<Resource<List<Rocket>>> {
        return withContext(Dispatchers.IO) {
            flow {
                emit(Resource.Loading())

                val localSavedRockets =
                    mapper.mapListRocketEntityToListRocket(appDb.rocketsDao().getLocalRockets())
                emit(Resource.Loading(localSavedRockets))

                try {
                    val remoteRockets = api.getRockets()
                    val localRockets = mapper.mapListRocketDtoToListRocketEntity(remoteRockets)
                    appDb.withTransaction {
                        appDb.rocketsDao().deleteAllRockets()
                        appDb.rocketsDao().insertRockets(localRockets)
                    }
                } catch (e: IOException) {
                    emit(Resource.Error(message = INTERNET_CONNECTION_ERROR))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = SOMETHING_WENT_WRONG))
                }

                val modelRockets =
                    mapper.mapListRocketEntityToListRocket(appDb.rocketsDao().getLocalRockets())
                emit(Resource.Success(modelRockets))
            }
        }
    }

    override suspend fun getLaunchesByRocketName(rocketName: String): Flow<Resource<List<Launch>>> {
        return withContext(Dispatchers.IO) {
            flow {
                emit(Resource.Loading())

                val localSavedLaunches = mapper.mapListLaunchEntityToListLaunch(
                    appDb.launchesDao().getLaunchesByRocketName(rocketName = rocketName)
                )
                Log.d("CHECKLAUNCHES", "localSavedLaunches: ${localSavedLaunches} в impl")
                emit(Resource.Loading(data = localSavedLaunches))

                try {
                    val remoteLaunches = api.getLaunchesByRocketId(rocketName = rocketName)
                    val entityLaunches = mapper.mapListLaunchDtoToListLaunchEntity(remoteLaunches)
                    appDb.withTransaction {
                        try {
                            appDb.launchesDao().deleteLaunchesByRocketName(rocketName = rocketName)
                        } catch (e: Exception) {
                            Log.d("MYERRORDAMN", "ERROR $e")
                        }

                        appDb.launchesDao().insertLaunches(entityLaunches)
                    }
                } catch (e: IOException) {
                    emit(Resource.Error(message = INTERNET_CONNECTION_ERROR))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = SOMETHING_WENT_WRONG))
                }

                val modelLaunches = mapper.mapListLaunchEntityToListLaunch(
                    appDb.launchesDao().getLaunchesByRocketName(rocketName = rocketName)
                )
                emit(Resource.Success(modelLaunches))
            }
        }
    }

    companion object {

        private const val INTERNET_CONNECTION_ERROR = "Нет интернет соединения"
        private const val SOMETHING_WENT_WRONG = "Что-то пошло не так, повторите позже"
    }

}