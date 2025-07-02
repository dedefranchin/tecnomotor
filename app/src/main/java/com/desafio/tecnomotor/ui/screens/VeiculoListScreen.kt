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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import com.desafio.tecnomotor.data.local.entity.VeiculoDetails
import com.desafio.tecnomotor.data.local.entity.VeiculoEntity
import com.desafio.tecnomotor.ui.components.ConfirmDialog
import com.desafio.tecnomotor.ui.screens.forms.VeiculoForm
import com.desafio.tecnomotor.ui.viewmodel.VeiculoListViewModel
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width

@Composable
fun VeiculoListScreen(
    navController: NavController,
    viewModel: VeiculoListViewModel = hiltViewModel()
) {
    val veiculos by viewModel.veiculos.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val montadora by viewModel.montadora.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingVeiculo by remember { mutableStateOf<VeiculoEntity?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var veiculoToDelete by remember { mutableStateOf<VeiculoEntity?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingVeiculo = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Veículo")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                montadora?.let {
                    Text(
                        text = "Veículos de ${it.nome}",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
            OutlinedTextField(
                value = searchQuery ?: "",
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Buscar por modelo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(veiculos) { veiculoDetails ->
                    VeiculoItem(
                        veiculoDetails = veiculoDetails,
                        onEditClick = {
                            editingVeiculo = veiculoDetails.veiculo
                            showDialog = true
                        },
                        onDeleteClick = {
                            veiculoToDelete = veiculoDetails.veiculo
                            showConfirmDialog = true
                        }
                    )
                }
            }
        }

        if (showDialog) {
            VeiculoForm(
                veiculo = editingVeiculo,
                onSave = { modelo, motorizacao, ano ->
                    viewModel.addOrUpdateVeiculo(editingVeiculo, modelo, motorizacao, ano)
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
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
}

@Composable
fun VeiculoItem(
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