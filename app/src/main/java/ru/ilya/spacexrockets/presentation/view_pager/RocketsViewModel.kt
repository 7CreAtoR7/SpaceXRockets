package ru.ilya.spacexrockets.presentation.view_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.domain.use_case.GetRocketsUseCase
import ru.ilya.spacexrockets.util.Resource
import ru.ilya.spacexrockets.presentation.rockets_screen.RocketsUIState
import javax.inject.Inject

class RocketsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<RocketsUIState>(RocketsUIState.Init)
    val state = _state.asStateFlow()

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