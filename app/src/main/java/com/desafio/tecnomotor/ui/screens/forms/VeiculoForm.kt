package com.desafio.tecnomotor.ui.screens.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.desafio.tecnomotor.data.local.entity.VeiculoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeiculoForm(
    veiculo: VeiculoEntity?,
    onSave: (String, String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var modelo by remember { mutableStateOf(veiculo?.modelo ?: "") }
    var motorizacao by remember { mutableStateOf(veiculo?.motorizacao ?: "") }
    var ano by remember { mutableStateOf(veiculo?.ano?.toString() ?: "") }

    val isModeloValid by remember(modelo) { mutableStateOf(modelo.length >= 2 && modelo.isNotBlank()) }
    val isMotorizacaoValid by remember(motorizacao) { mutableStateOf(motorizacao.isNotBlank()) }
    val isAnoValid by remember(ano) { mutableStateOf(ano.toIntOrNull() != null) }

    val isFormValid by remember(isModeloValid, isMotorizacaoValid, isAnoValid) {
        mutableStateOf(isModeloValid && isMotorizacaoValid && isAnoValid)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (veiculo == null) "Novo Veículo" else "Editar Veículo") },
        text = {
            Column {
                OutlinedTextField(
                    value = modelo,
                    onValueChange = { modelo = it },
                    label = { Text("Modelo") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isModeloValid,
                    supportingText = {
                        if (!isModeloValid) {
                            Text("O modelo deve ter pelo menos 2 caracteres.")
                        }
                    }
                )
                OutlinedTextField(
                    value = motorizacao,
                    onValueChange = { motorizacao = it },
                    label = { Text("Motorização") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isMotorizacaoValid,
                    supportingText = {
                        if (!isMotorizacaoValid) {
                            Text("A motorização é obrigatória.")
                        }
                    }
                )
                OutlinedTextField(
                    value = ano,
                    onValueChange = { ano = it },
                    label = { Text("Ano") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isAnoValid,
                    supportingText = {
                        if (!isAnoValid) {
                            Text("O ano é obrigatório.")
                        }
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(modelo, motorizacao, ano.toIntOrNull() ?: 0)
                },
                enabled = isFormValid
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
} 