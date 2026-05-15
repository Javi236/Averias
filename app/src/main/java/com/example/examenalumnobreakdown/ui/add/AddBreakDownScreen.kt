package com.example.examenalumnobreakdown.ui.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.breakdown.R
import com.example.examenalumnobreakdown.ui.base.components.AlertDialogOk

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBreakDownScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    viewModel: AddViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val exists = viewModel.exists

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (state.isEditing) "Editar Avería" else "Alta de Avería",
                        fontWeight = FontWeight.Bold
                    )
                },
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
            FloatingActionButton(
                onClick = { viewModel.onSave(onBack) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(id = R.string.save_description)
                )
            }
        }
    ) { innerPadding ->
        ManageBreakDownContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            state = state
        )
    }

    if (exists) {
        AlertDialogOk(
            title = "Error",
            text = "El código de avería ya existe",
            onOk = { /* El usuario corrige el código */ }
        )
    }

    if (viewModel.menor) {
        AlertDialogOk(
            title = "Error",
            text = "El código debe tener al menos 3 caracteres",
            onOk = { /* El usuario corrige el código */ }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBreakDownContent(
    modifier: Modifier = Modifier,
    viewModel: AddViewModel,
    state: AddState
) {
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo Código
        OutlinedTextField(
            value = state.code,
            onValueChange = { viewModel.onChangeCode(it) },
            label = { Text(stringResource(id = R.string.tvCode)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !state.isEditing
        )

        // Selector de Ciudad (Dropdown)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            val selectedCityName = state.availableCities.find { it.id == state.ciudadId }?.nombre ?: "Selecciona una ciudad"
            
            OutlinedTextField(
                value = selectedCityName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Ciudad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                state.availableCities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city.nombre) },
                        onClick = {
                            viewModel.onChangeCityId(city.id)
                            expanded = false
                        }
                    )
                }
            }
        }

        // Campo Persona
        OutlinedTextField(
            value = state.person,
            onValueChange = { viewModel.onChangePerson(it) },
            label = { Text(stringResource(id = R.string.tvPerson)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Campo Fecha
        OutlinedTextField(
            value = state.date,
            onValueChange = { viewModel.onChangeDate(it) },
            label = { Text(stringResource(id = R.string.tvTitleDate)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Campo Descripción
        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.onChangeDescription(it) },
            label = { Text(stringResource(id = R.string.tvDescription)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}
