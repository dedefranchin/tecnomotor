package com.desafio.tecnomotor.ui.viewmodel

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
class AllVeiculosViewModel @Inject constructor(
    private val repository: TecnomotorRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery

    private val _selectedMontadoraId = MutableStateFlow<Int?>(null)
    val selectedMontadoraId: StateFlow<Int?> = _selectedMontadoraId

    val montadoras: StateFlow<List<MontadoraEntity>> = repository.getAllMontadoras()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val veiculos: StateFlow<List<VeiculoDetails>> =
        combine(_searchQuery, _selectedMontadoraId) { query, montadoraId ->
            Pair(query, montadoraId)
        }.flatMapLatest { (query, montadoraId) ->
            repository.getVeiculosDetails(montadoraId, query)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = if (query.isBlank()) null else query
    }

    fun onMontadoraSelected(montadoraId: Int?) {
        _selectedMontadoraId.value = montadoraId
    }

    fun updateVeiculo(
        veiculoToUpdate: VeiculoEntity,
        modelo: String,
        motorizacao: String,
        ano: Int
    ) {
        viewModelScope.launch {
            val updatedVeiculo = veiculoToUpdate.copy(
                modelo = modelo,
                motorizacao = motorizacao,
                ano = ano,
                dtUpd = Date()
            )
            repository.updateVeiculo(updatedVeiculo)
        }
    }

    fun deleteVeiculo(veiculo: VeiculoEntity) {
        viewModelScope.launch {
            repository.deleteVeiculo(veiculo)
        }
    }
} 