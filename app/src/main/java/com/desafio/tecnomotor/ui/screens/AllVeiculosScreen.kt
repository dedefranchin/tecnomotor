package com.desafio.tecnomotor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.desafio.tecnomotor.data.local.entity.VeiculoDetails
import com.desafio.tecnomotor.data.local.entity.VeiculoEntity
import com.desafio.tecnomotor.ui.components.ConfirmDialog
import com.desafio.tecnomotor.ui.components.MontadoraDropdown
import com.desafio.tecnomotor.ui.screens.forms.VeiculoForm
import com.desafio.tecnomotor.ui.viewmodel.AllVeiculosViewModel

@Composable
fun AllVeiculosScreen(
    viewModel: AllVeiculosViewModel = hiltViewModel()
) {
    val veiculos by viewModel.veiculos.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val montadoras by viewModel.montadoras.collectAsState()
    val selectedMontadoraId by viewModel.selectedMontadoraId.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var editingVeiculo by remember { mutableStateOf<VeiculoEntity?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var veiculoToDelete by remember { mutableStateOf<VeiculoEntity?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery ?: "",
            onValueChange = viewModel::onSearchQueryChange,
            label = { Text("Buscar por modelo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        MontadoraDropdown(
            montadoras = montadoras,
            selectedMontadoraId = selectedMontadoraId,
            onMontadoraSelected = viewModel::onMontadoraSelected,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = "${veiculos.size} registros encontrados",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            items(veiculos) { veiculoDetails ->
                VeiculoMontadoraItem(
                    veiculoDetails = veiculoDetails,
                    onEditClick = {
                        editingVeiculo = veiculoDetails.veiculo
                        showEditDialog = true
                    },
                    onDeleteClick = {
                        veiculoToDelete = veiculoDetails.veiculo
                        showConfirmDialog = true
                    }
                )
            }
        }
    }

    if (showEditDialog) {
        editingVeiculo?.let {
            VeiculoForm(
                veiculo = it,
                onSave = { modelo, motorizacao, ano ->
                    viewModel.updateVeiculo(it, modelo, motorizacao, ano)
                    showEditDialog = false
                },
                onDismiss = { showEditDialog = false }
            )
        }
    }

    if (showConfirmDialog) {
        veiculoToDelete?.let { veiculo ->
            ConfirmDialog(
                title = "Excluir Veículo",
                message = "Tem certeza que deseja excluir o veículo ${veiculo.modelo}?",
                onConfirm = {
                    viewModel.deleteVeiculo(veiculo)
                    showConfirmDialog = false
                    veiculoToDelete = null
                },
                onDismiss = {
                    showConfirmDialog = false
                    veiculoToDelete = null
                }
            )
        }
    }
}

@Composable
fun VeiculoMontadoraItem(
    veiculoDetails: VeiculoDetails,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Modelo: ${veiculoDetails.veiculo.modelo}")
                Text(text = "Montadora: ${veiculoDetails.nomeMontadora ?: "N/A"}")
                Text(text = "Ano: ${veiculoDetails.veiculo.ano}")
                Text(text = "Motor: ${veiculoDetails.veiculo.motorizacao}")
            }
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Veículo")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir Veículo")
                }
            }
        }
    }
} 