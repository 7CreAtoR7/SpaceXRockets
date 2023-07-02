package ru.ilya.spacexrockets.presentation.launches_screen

import ru.ilya.spacexrockets.domain.model.launches_model.Launch

sealed class LaunchesUIState {
    object Init : LaunchesUIState()
    data class Loading(val launchesListFromLastSession: List<Launch>) : LaunchesUIState()
    data class Success(val launchesList: List<Launch>) : LaunchesUIState()
    data class Error(val message: String) : LaunchesUIState()
}