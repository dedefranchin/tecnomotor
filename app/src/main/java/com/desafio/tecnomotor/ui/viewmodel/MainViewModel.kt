package com.desafio.tecnomotor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafio.tecnomotor.repository.TecnomotorRepository
import com.desafio.tecnomotor.ui.navigation.Routes
import com.desafio.tecnomotor.util.NetworkStatus
import com.desafio.tecnomotor.util.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val startDestination: String) : MainUiState()
}


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TecnomotorRepository,
    networkUtils: NetworkUtils
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    val networkStatus: StateFlow<NetworkStatus> = networkUtils.networkStatus.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkStatus.Unavailable
    )

    init {
        checkInitialDestination()
    }

    private fun checkInitialDestination() {
        viewModelScope.launch {
            val count = repository.getMontadoraCount()
            if (count > 0) {
                _uiState.value = MainUiState.Success(Routes.MONTADORA_LIST)
            } else {
                _uiState.value = MainUiState.Success(Routes.SYNC)
            }
        }
    }
} 