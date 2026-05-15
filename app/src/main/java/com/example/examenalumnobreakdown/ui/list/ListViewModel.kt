package com.example.examenalumnobreakdown.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenalumnobreakdown.data.model.BreakDown
import com.example.examenalumnobreakdown.data.repository.BreakDownRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: BreakDownRepository
): ViewModel() {
    var state by mutableStateOf<ListState>(ListState.Loading)

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            repository.getAllBreakDowns().collect { list ->
                state = if (list.isEmpty()) {
                    ListState.NoData
                } else {
                    ListState.Success(list)
                }
            }
        }
    }

    fun delete(breakDown: BreakDown){
        viewModelScope.launch {
            repository.deleteBreakDown(breakDown)
        }
    }
}