package com.desafio.tecnomotor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity
import com.desafio.tecnomotor.repository.TecnomotorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MontadoraListViewModel @Inject constructor(
    private val repository: TecnomotorRepository
) : ViewModel() {

    val montadoras: StateFlow<List<MontadoraEntity>> = repository.getAllMontadoras()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addOrUpdateMontadora(montadora: MontadoraEntity?, nome: String) {
        viewModelScope.launch {
            if (montadora == null) {
                // New montadora
                val newMontadora = MontadoraEntity(
                    nome = nome,
                    dtIns = Date(),
                    dtUpd = Date()
                )
                repository.insertMontadora(newMontadora)
            } else {
                // Update existing montadora
                val updatedMontadora = montadora.copy(
                    nome = nome,
                    dtUpd = Date()
                )
                repository.updateMontadora(updatedMontadora)
            }
        }
    }

    fun deleteMontadora(montadora: MontadoraEntity) {
        viewModelScope.launch {
            repository.deleteMontadora(montadora)
        }
    }
} 