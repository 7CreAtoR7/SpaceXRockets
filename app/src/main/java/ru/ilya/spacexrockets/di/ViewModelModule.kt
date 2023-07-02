package ru.ilya.spacexrockets.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ilya.spacexrockets.presentation.launches_screen.LaunchesViewModel
import ru.ilya.spacexrockets.presentation.view_pager.RocketsViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RocketsViewModel::class)
    fun bindRocketsViewModel(viewModel: RocketsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LaunchesViewModel::class)
    fun bindLaunchesViewModel(viewModel: LaunchesViewModel): ViewModel

}