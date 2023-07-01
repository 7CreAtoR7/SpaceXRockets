package ru.ilya.spacexrockets.util

import ru.ilya.spacexrockets.domain.model.Rocket

sealed class RocketsUIState {
    object Init : RocketsUIState()
    data class Loading(val rocketsListFromLastSession: List<Rocket>) : RocketsUIState()
    data class Success(val rocketsList: List<Rocket>) : RocketsUIState()
    data class Error(val message: String) : RocketsUIState()
}