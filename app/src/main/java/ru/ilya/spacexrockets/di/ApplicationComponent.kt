package ru.ilya.spacexrockets.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.ilya.spacexrockets.presentation.MainActivity
import ru.ilya.spacexrockets.presentation.launches_screen.LaunchesFragment
import ru.ilya.spacexrockets.presentation.view_pager.RocketsViewPagerFragment

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: RocketsViewPagerFragment)

    fun inject(fragment: LaunchesFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}