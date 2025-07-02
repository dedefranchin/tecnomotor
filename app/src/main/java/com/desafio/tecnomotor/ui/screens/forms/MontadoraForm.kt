package com.desafio.tecnomotor.ui.screens.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MontadoraForm(
    montadora: MontadoraEntity?,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var nome by remember {
        mutableStateOf(
            TextFieldValue(
                text = montadora?.nome ?: "",
                selection = TextRange((montadora?.nome ?: "").length)
            )
        )
    }
    val isNomeValid by remember(nome) { mutableStateOf(nome.text.isNotBlank()) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (montadora == null) "Nova Montadora" else "Editar Montadora") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome da Montadora") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    isError = !isNomeValid,
                    supportingText = {
                        if (!isNomeValid) {
                            Text("O nome é obrigatório.")
                        }
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(nome.text)
                },
                enabled = isNomeValid
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