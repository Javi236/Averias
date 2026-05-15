package com.example.examenalumnobreakdown.ui.city

import com.example.examenalumnobreakdown.data.model.City

data class CityState(
    val cities: List<City> = emptyList(),
    val id: Int = 0,
    val nombre: String = "",
    val cp: String = "",
    val poblacion: String = "",
    val isEditing: Boolean = false
)