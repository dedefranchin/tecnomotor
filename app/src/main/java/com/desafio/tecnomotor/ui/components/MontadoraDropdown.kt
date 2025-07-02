package com.desafio.tecnomotor.ui.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MontadoraDropdown(
    montadoras: List<MontadoraEntity>,
    selectedMontadoraId: Int?,
    onMontadoraSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedMontadoraText = montadoras.find { it.id == selectedMontadoraId }?.nome ?: "Todas"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedMontadoraText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Filtrar por montadora") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Todas") },
                onClick = {
                    onMontadoraSelected(null)
                    expanded = false
                }
            )
            montadoras.forEach { montadora ->
                DropdownMenuItem(
                    text = { Text(montadora.nome) },
                    onClick = {
                        onMontadoraSelected(montadora.id)
                        expanded = false
                    }
                )
            }
        }
    }
} 