package com.example.examenalumnobreakdown.ui.list

import com.example.examenalumnobreakdown.data.model.BreakDown


sealed class ListState {
    data object Loading: ListState()
    data object NoData: ListState()
    data class Success(var list: List<BreakDown>): ListState()
}