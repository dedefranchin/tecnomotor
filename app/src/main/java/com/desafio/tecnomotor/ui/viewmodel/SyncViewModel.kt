package com.desafio.tecnomotor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafio.tecnomotor.repository.TecnomotorRepository
import com.desafio.tecnomotor.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class SyncUiState {
    object Idle : SyncUiState()
    object Loading : SyncUiState()
    object Success : SyncUiState()
    data class Error(val message: String) : SyncUiState()
}

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val repository: TecnomotorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SyncUiState>(SyncUiState.Idle)
    val uiState: StateFlow<SyncUiState> = _uiState

    fun sync() {
        viewModelScope.launch {
            _uiState.value = SyncUiState.Loading
            try {
                repository.sync()
                _uiState.value = SyncUiState.Success
            } catch (e: HttpException) {
                _uiState.value = SyncUiState.Error("Erro de comunicação com o servidor.")
            } catch (e: IOException) {
                _uiState.value = SyncUiState.Error("Falha na conexão. Verifique a internet.")
            } catch (e: Exception) {
                _uiState.value = SyncUiState.Error("Ocorreu um erro na sincronização.")
            }
        }
    }
} 