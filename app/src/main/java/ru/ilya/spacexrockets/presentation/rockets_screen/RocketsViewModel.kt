package ru.ilya.spacexrockets.presentation.rockets_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.domain.use_case.GetRocketsUseCase
import ru.ilya.spacexrockets.util.Resource
import ru.ilya.spacexrockets.util.RocketsUIState
import javax.inject.Inject

class RocketsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<RocketsUIState>(RocketsUIState.Init)
    val state = _state.asSharedFlow()

    fun getRockets() {
        viewModelScope.launch(Dispatchers.IO) {
            getRocketsUseCase()
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = RocketsUIState.Success(
                                rocketsList = result.data ?: emptyList()
                            )
                        }
                        is Resource.Error -> {
                            _state.emit(
                                RocketsUIState.Error(
                                    message = result.message ?: SOMETHING_WENT_WRONG
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _state.emit(
                                RocketsUIState.Loading(
                                    rocketsListFromLastSession = result.data ?: emptyList()
                                )
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    companion object {

        private const val INTERNET_CONNECTION_ERROR = "Нет интернет соединения"
        private const val SOMETHING_WENT_WRONG = "Что-то пошло не так, повторите позже"
    }
}