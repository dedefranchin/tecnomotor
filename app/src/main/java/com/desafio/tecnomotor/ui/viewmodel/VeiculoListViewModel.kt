package com.desafio.tecnomotor.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity
import com.desafio.tecnomotor.data.local.entity.VeiculoDetails
import com.desafio.tecnomotor.data.local.entity.VeiculoEntity
import com.desafio.tecnomotor.repository.TecnomotorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class VeiculoListViewModel @Inject constructor(
    private val repository: TecnomotorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val montadoraId: Int = checkNotNull(savedStateHandle["montadoraId"])

    private val _montadora = MutableStateFlow<MontadoraEntity?>(null)
    val montadora: StateFlow<MontadoraEntity?> = _montadora

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery

    val veiculos: StateFlow<List<VeiculoDetails>> = _searchQuery.flatMapLatest { query ->
        repository.getVeiculosDetails(montadoraId, query)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            _montadora.value = repository.getMontadoraById(montadoraId)
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = if (query.isBlank()) null else query
    }

    fun addOrUpdateVeiculo(
        veiculo: VeiculoEntity?,
        modelo: String,
        motorizacao: String,
        ano: Int
    ) {
        viewModelScope.launch {
            if (veiculo == null) {
                val newVeiculo = VeiculoEntity(
                    modelo = modelo,
                    motorizacao = motorizacao,
                    ano = ano,
                    idMontadora = montadoraId,
                    dtIns = Date(),
                    dtUpd = Date()
                )
                repository.insertVeiculo(newVeiculo)
            } else {
                val updatedVeiculo = veiculo.copy(
                    modelo = modelo,
                    motorizacao = motorizacao,
                    ano = ano,
                    dtUpd = Date()
                )
                repository.updateVeiculo(updatedVeiculo)
            }
        }
    }

    fun deleteVeiculo(veiculo: VeiculoEntity) {
        viewModelScope.launch {
            repository.deleteVeiculo(veiculo)
        }
    }
} 