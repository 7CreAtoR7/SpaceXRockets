package ru.ilya.spacexrockets.presentation.launches_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.domain.use_case.GetLaunchesUseCase
import ru.ilya.spacexrockets.util.Resource
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<LaunchesUIState>(LaunchesUIState.Init)
    val state = _state.asStateFlow()

    fun getLaunchesByRocketName(rocketName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getLaunchesUseCase(rocketName = rocketName)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = LaunchesUIState.Success(
                                launchesList = result.data ?: emptyList()
                            )
                        }
                        is Resource.Error -> {
                            _state.emit(
                                LaunchesUIState.Error(
                                    message = result.message ?: SOMETHING_WENT_WRONG
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _state.emit(
                                LaunchesUIState.Loading(
                                    launchesListFromLastSession = result.data ?: emptyList()
                                )
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    companion object {

        private const val SOMETHING_WENT_WRONG = "Что-то пошло не так, повторите позже"
    }
}