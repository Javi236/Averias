package com.example.examenalumnobreakdown.ui.city

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.examenalumnobreakdown.data.model.City
import com.example.examenalumnobreakdown.ui.base.components.AlertDialogOkCancel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCityScreen(
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (City) -> Unit,
    viewModel: CityViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var cityToDelete by remember { mutableStateOf<City?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gestión de Ciudades", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Ciudad")
            }
        }
    ) { padding ->
        if (state.cities.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No hay ciudades registradas")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.cities) { city ->
                    CityItem(
                        city = city,
                        onEdit = { 
                            viewModel.onEditCity(city)
                            onEdit(city) 
                        },
                        onDelete = { cityToDelete = city }
                    )
                }
            }
        }
    }

    if (cityToDelete != null) {
        AlertDialogOkCancel(
            title = "¡Atención!",
            text = "¿Estás seguro de que quieres eliminar la ciudad '${cityToDelete?.nombre}'? Se borrarán TODAS las averías asociadas a esta ciudad.",
            okText = "Eliminar todo",
            cancelText = "Cancelar",
            onConfirm = {
                cityToDelete?.let { viewModel.onDeleteCity(it) }
                cityToDelete = null
            },
            onDismiss = { cityToDelete = null }
        )
    }
}

@Composable
fun CityItem(city: City, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = city.nombre, style = MaterialTheme.typography.titleLarge)
                Text(text = "CP: ${city.cp}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Población: ${city.poblacion}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Blue)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
            }
        }
    }
}
