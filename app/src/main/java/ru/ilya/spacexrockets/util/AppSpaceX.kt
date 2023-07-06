package ru.ilya.spacexrockets.util

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import ru.ilya.spacexrockets.data.local.AppDatabase
import ru.ilya.spacexrockets.data.mapper.AppMapper
import ru.ilya.spacexrockets.data.remote.SpaceXApi
import ru.ilya.spacexrockets.data.remote.workers.CheckNewRocketsWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class AppSpaceX : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CheckNewRocketsWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

}