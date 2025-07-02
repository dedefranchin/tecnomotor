package com.desafio.tecnomotor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.desafio.tecnomotor.ui.viewmodel.SyncUiState
import com.desafio.tecnomotor.ui.viewmodel.SyncViewModel

@Composable
fun SyncScreen(
    viewModel: SyncViewModel = hiltViewModel(),
    onSyncSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is SyncUiState.Idle -> {
                Button(onClick = { viewModel.sync() }) {
                    Text("Sincronizar")
                }
            }
            is SyncUiState.Loading -> {
                CircularProgressIndicator()
                Text(text = "Sincronizando...", modifier = Modifier.padding(top = 16.dp))
            }
            is SyncUiState.Success -> {
                onSyncSuccess()
            }
            is SyncUiState.Error -> {
                Text(text = "Erro na sincronização: ${state.message}")
                Button(onClick = { viewModel.sync() }, modifier = Modifier.padding(top = 16.dp)) {
                    Text("Tentar Novamente")
                }
            }
        }
    }
} 