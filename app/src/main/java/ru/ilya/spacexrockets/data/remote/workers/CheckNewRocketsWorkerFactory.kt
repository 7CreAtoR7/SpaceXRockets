package ru.ilya.spacexrockets.data.remote.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.ilya.spacexrockets.data.local.AppDatabase
import ru.ilya.spacexrockets.data.mapper.AppMapper
import ru.ilya.spacexrockets.data.remote.SpaceXApi
import javax.inject.Inject

class CheckNewRocketsWorkerFactory @Inject constructor(
    private val appDb: AppDatabase,
    private val api: SpaceXApi,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return CheckNewRocketsWorker(
            appContext,
            workerParameters,
            appDb,
            api
        )
    }

}