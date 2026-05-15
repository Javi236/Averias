package com.example.examenalumnobreakdown.ui.city

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenalumnobreakdown.data.model.City
import com.example.examenalumnobreakdown.data.repository.BreakDownRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: BreakDownRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CityState())
    val state: StateFlow<CityState> = _state.asStateFlow()

    init {
        getCities()
        val id: Int? = savedStateHandle["id"]
        if (id != null && id != -1) {
            loadCity(id)
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            repository.getAllCities().collectLatest { cities ->
                _state.update { it.copy(cities = cities) }
            }
        }
    }

    private fun loadCity(id: Int) {
        viewModelScope.launch {
            repository.getAllCities().collectLatest { cities ->
                val city = cities.find { it.id == id }
                city?.let { onEditCity(it) }
            }
        }
    }

    fun onNombreChange(nombre: String) {
        _state.update { it.copy(nombre = nombre) }
    }

    fun onCPChange(cp: String) {
        _state.update { it.copy(cp = cp) }
    }

    fun onPoblacionChange(poblacion: String) {
        _state.update { it.copy(poblacion = poblacion) }
    }

    fun onEditCity(city: City) {
        _state.update {
            it.copy(
                id = city.id,
                nombre = city.nombre,
                cp = city.cp,
                poblacion = city.poblacion.toString(),
                isEditing = true
            )
        }
    }

    fun resetForm() {
        _state.update {
            it.copy(
                id = 0,
                nombre = "",
                cp = "",
                poblacion = "",
                isEditing = false
            )
        }
    }

    fun onSaveCity(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val currentState = _state.value
            val city = City(
                id = if (currentState.isEditing) currentState.id else 0,
                nombre = currentState.nombre,
                cp = currentState.cp,
                poblacion = currentState.poblacion.toIntOrNull() ?: 0
            )
            if (currentState.isEditing) {
                repository.updateCity(city)
            } else {
                repository.insertCity(city)
            }
            resetForm()
            onSuccess()
        }
    }

    fun onDeleteCity(city: City) {
        viewModelScope.launch {
            repository.deleteCity(city)
        }
    }
}