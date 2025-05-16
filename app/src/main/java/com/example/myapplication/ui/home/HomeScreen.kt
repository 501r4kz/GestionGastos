package com.example.myapplication.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    ingresos: Int,
    gastos: List<Pair<String, Int>>,
    onAgregarIngreso: (Int) -> Unit,
    onAgregarGasto: (String, Int, String) -> Unit
) {
    var ingresoInput by remember { mutableStateOf("") }
    var gastoCantidad by remember { mutableStateOf("") }
    var gastoDescripcion by remember { mutableStateOf("") }
    var gastoCategoria by remember { mutableStateOf("Alimentos") }

    val categorias = listOf("Alimentos", "Transporte", "Entretenimiento")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF3FA))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Resumen del Mes", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Text("¿Cuánto recibiste este mes?")

        // Input de ingreso
        OutlinedTextField(
            value = ingresoInput,
            onValueChange = { ingresoInput = it },
            label = { Text("$") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para guardar ingreso
        Button(
            onClick = {
                ingresoInput.toIntOrNull()?.let {
                    onAgregarIngreso(it)
                    ingresoInput = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6EAF8))
        ) {
            Text("Guardar ingreso", color = Color.Black)
        }

        Divider()

        // Sección de gasto
        Text("Agregar Gasto", fontWeight = FontWeight.SemiBold)


        var expanded by remember { mutableStateOf(false) }
        @OptIn(ExperimentalMaterial3Api::class)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = gastoCategoria,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria) },
                        onClick = {
                            gastoCategoria = categoria
                            expanded = false
                        }
                    )
                }
            }
        }


        // Cantidad gastada
        OutlinedTextField(
            value = gastoCantidad,
            onValueChange = { gastoCantidad = it },
            label = { Text("Cantidad gastada") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Descripción
        OutlinedTextField(
            value = gastoDescripcion,
            onValueChange = { gastoDescripcion = it },
            label = { Text("Descripción") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para agregar gasto
        Button(
            onClick = {
                val cantidad = gastoCantidad.toIntOrNull()
                if (cantidad != null) {
                    onAgregarGasto(gastoCategoria, cantidad, gastoDescripcion)
                    gastoCantidad = ""
                    gastoDescripcion = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6EAF8))
        ) {
            Text("Agregar gasto", color = Color.Black)
        }

        // Lista de gastos
        Spacer(modifier = Modifier.height(16.dp))
        gastos.forEach { (nombre, cantidad) ->
            Text(
                text = "$nombre    $${cantidad}",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
