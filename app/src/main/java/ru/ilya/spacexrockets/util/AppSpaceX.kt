package ru.ilya.spacexrockets.util

import android.app.Application
import ru.ilya.spacexrockets.di.DaggerApplicationComponent

class AppSpaceX : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}