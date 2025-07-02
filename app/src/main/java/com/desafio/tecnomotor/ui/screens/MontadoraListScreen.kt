package com.desafio.tecnomotor.ui.screens

import androidx.compose.foundation.clickable
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
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity
import com.desafio.tecnomotor.ui.components.ConfirmDialog
import com.desafio.tecnomotor.ui.screens.forms.MontadoraForm
import com.desafio.tecnomotor.ui.viewmodel.MontadoraListViewModel

@Composable
fun MontadoraListScreen(
    viewModel: MontadoraListViewModel = hiltViewModel(),
    onMontadoraClick: (Int) -> Unit,
) {
    val montadoras by viewModel.montadoras.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingMontadora by remember { mutableStateOf<MontadoraEntity?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var montadoraToDelete by remember { mutableStateOf<MontadoraEntity?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingMontadora = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Montadora")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(montadoras) { montadora ->
                MontadoraItem(
                    montadora = montadora,
                    onItemClick = { onMontadoraClick(montadora.id) },
                    onEditClick = {
                        editingMontadora = montadora
                        showDialog = true
                    },
                    onDeleteClick = {
                        montadoraToDelete = montadora
                        showConfirmDialog = true
                    }
                )
            }
        }

        if (showDialog) {
            MontadoraForm(
                montadora = editingMontadora,
                onSave = { nome ->
                    viewModel.addOrUpdateMontadora(editingMontadora, nome)
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }

        if (showConfirmDialog) {
            montadoraToDelete?.let { montadora ->
                ConfirmDialog(
                    title = "Excluir Montadora",
                    message = "Tem certeza que deseja excluir a montadora ${montadora.nome}?",
                    onConfirm = {
                        viewModel.deleteMontadora(montadora)
                        showConfirmDialog = false
                        montadoraToDelete = null
                    },
                    onDismiss = {
                        showConfirmDialog = false
                        montadoraToDelete = null
                    }
                )
            }
        }
    }
}

@Composable
fun MontadoraItem(
    montadora: MontadoraEntity,
    onItemClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onItemClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = montadora.nome,
                modifier = Modifier.weight(1f)
            )
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Montadora")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir Montadora")
                }
            }
        }
    }
} 