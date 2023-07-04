package ru.ilya.spacexrockets.presentation.view_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ilya.spacexrockets.domain.use_case.GetRocketsUseCase
import ru.ilya.spacexrockets.presentation.rockets_screen.RocketsUIState
import ru.ilya.spacexrockets.util.Resource
import javax.inject.Inject

@HiltViewModel
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

        private const val SOMETHING_WENT_WRONG = "Что-то пошло не так, повторите позже"
    }
}