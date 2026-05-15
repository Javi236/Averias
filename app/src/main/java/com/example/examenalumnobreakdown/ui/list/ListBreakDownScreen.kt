package com.example.examenalumnobreakdown.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.examenalumnobreakdown.ui.base.components.AlertDialogOkCancel
import com.example.examenalumnobreakdown.ui.base.components.LoadingUi
import com.example.examenalumnobreakdown.ui.base.components.NoDataScreen
import com.example.breakdown.ui.base.icon_composables.breakIcon
import com.example.examenalumnobreakdown.data.model.BreakDown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBreakDownScreen(
    modifier: Modifier,
    onAdd: () -> Unit,
    onEdit: (BreakDown) -> Unit,
    onNavigateToCities: () -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "CityServices - Averías",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToCities) {
                        Icon(imageVector = Icons.Default.LocationCity, contentDescription = "Gestionar Ciudades")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAdd() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Añadir avería"
                )
            }
        }
    ) { padding ->
        when (state) {
            is ListState.Loading -> LoadingUi()
            is ListState.NoData -> NoDataScreen()
            is ListState.Success -> {
                BreakDownListContent(
                    modifier = Modifier.padding(padding),
                    items = state.list,
                    onEdit = onEdit,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun BreakDownListContent(
    modifier: Modifier,
    items: List<BreakDown>,
    onEdit: (BreakDown) -> Unit,
    viewModel: ListViewModel
) {
    var onShowDialog by remember { mutableStateOf(false) }
    var breakToDelete by remember { mutableStateOf<BreakDown?>(null) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            BreakdownItem(
                breakDown = item,
                modifier = Modifier.clickable { onEdit(item) },
                onLongClick = {
                    breakToDelete = item
                    onShowDialog = true
                }
            )
        }
    }

    if (onShowDialog) {
        AlertDialogOkCancel(
            title = "Eliminar Avería",
            text = "¿Estás seguro de que deseas eliminar esta avería?",
            okText = "Confirmar",
            cancelText = "Cancelar",
            onConfirm = {
                breakToDelete?.let { viewModel.delete(it) }
                onShowDialog = false
            },
            onDismiss = {
                onShowDialog = false
            }
        )
    }
}

@Composable
fun BreakdownItem(
    modifier: Modifier = Modifier, 
    breakDown: BreakDown,
    onLongClick: () -> Unit = {}
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            ) {
                Image(
                    imageVector = breakIcon(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            Column(modifier = Modifier.padding(horizontal = 16.dp).weight(1f)) {
                Text(
                    text = breakDown.code,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Reportado por: ${breakDown.person}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = breakDown.date,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            // Botón de borrado rápido o simplemente el long click que ya manejamos en el padre
        }
    }
}
