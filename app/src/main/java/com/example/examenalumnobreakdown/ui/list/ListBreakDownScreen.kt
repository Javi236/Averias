package com.example.examenalumnobreakdown.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
                title = { Text("CityServices - Averías", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToCities) {
                        Icon(imageVector = Icons.Default.LocationCity, contentDescription = "Ciudades")
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
            FloatingActionButton(onClick = onAdd) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
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
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items) { item ->
            BreakdownItem(
                breakDown = item,
                onEdit = { onEdit(item) },
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
            text = "¿Estás seguro de que deseas eliminar la avería ${breakToDelete?.code}?",
            okText = "Eliminar",
            cancelText = "Cancelar",
            onConfirm = {
                breakToDelete?.let { viewModel.delete(it) }
                onShowDialog = false
            },
            onDismiss = { onShowDialog = false }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BreakdownItem(
    breakDown: BreakDown,
    onEdit: () -> Unit,
    onLongClick: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onEdit,
                onLongClick = onLongClick
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
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
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }
            Column(modifier = Modifier.padding(horizontal = 16.dp).weight(1f)) {
                Text(
                    text = "Cód: ${breakDown.code}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Ciudad ID: ${breakDown.ciudadId}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Persona: ${breakDown.person}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
