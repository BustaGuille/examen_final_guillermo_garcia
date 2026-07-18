package com.guillermogarcia.examenfinal2026

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.guillermogarcia.examenfinal2026.ui.theme.ExamenFinal2026Theme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val TAG = "ExamenLog"
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenFinal2026Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            shadowElevation = 4.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .statusBarsPadding()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Registro de Empleados",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    MainScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val empleados = viewModel.empleados

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            EmployeeForm(onAdd = { viewModel.agregarEmpleado(it) })
        }

        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Lista de Empleados",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(empleados, key = { it.id }) { empleado ->
            EmployeeItem(
                empleado = empleado,
                onDelete = { viewModel.eliminarEmpleado(empleado) }
            )
        }
    }
}

@Composable
fun EmployeeForm(onAdd: (Empleado) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var depto by remember { mutableStateOf("") }
    var salario by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Ingresar Datos", fontWeight = FontWeight.Bold)
            
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cargo,
                onValueChange = { cargo = it },
                label = { Text("Cargo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = depto,
                onValueChange = { depto = it },
                label = { Text("Departamento") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = salario,
                onValueChange = { salario = it },
                label = { Text("Salario") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha de Contratación (DD/MM/AAAA)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (nombre.isNotBlank() && cargo.isNotBlank() && depto.isNotBlank() &&
                        salario.isNotBlank() && fecha.isNotBlank()
                    ) {
                        onAdd(
                            Empleado(
                                nombreCompleto = nombre,
                                cargo = cargo,
                                departamento = depto,
                                salario = salario.toDoubleOrNull() ?: 0.0,
                                fechaContratacion = fecha
                            )
                        )
                        nombre = ""; cargo = ""; depto = ""; salario = ""; fecha = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Registrar")
            }
        }
    }
}

@Composable
fun EmployeeItem(empleado: Empleado, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = empleado.nombreCompleto,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            val formatGs = NumberFormat.getIntegerInstance(Locale("es", "PY"))
            val salarioGs = "Gs. ${formatGs.format(empleado.salario)}"

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item { DataChip("Cargo: ${empleado.cargo}") }
                item { DataChip("Dpto: ${empleado.departamento}") }
                item { DataChip("Sal: $salarioGs") }
                item { DataChip("Fec: ${empleado.fechaContratacion}") }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun DataChip(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
