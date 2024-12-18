package com.example.trackcap.ui.common.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listSelector(
    modifier: Modifier = Modifier,
    label: String,
    options: List<String>,
    onSelected: (String) -> Unit,
    defaultValue: String = ""
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOptionText = remember { mutableStateOf(defaultValue) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }, // Alterna entre expandido y colapsado
        modifier = modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = selectedOptionText.value,
            onValueChange = {}, // No permitimos edición
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            readOnly = true, // Campo de solo lectura
            modifier = Modifier
                .menuAnchor() // Necesario para anclar el menú correctamente
                .fillMaxWidth()
                .clickable { expanded = !expanded } // Alterna el estado del menú
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOptionText.value = selectionOption
                        expanded = false
                        onSelected(selectionOption)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun listSelectorPreview() {
    val options = listOf("Día", "Semana", "Mes", "Año", "Decada")
    val label = "Temporalidad"
    listSelector(onSelected = {}, options = options, label = label)
}