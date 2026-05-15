package com.example.examenalumnobreakdown.ui.add

import com.example.examenalumnobreakdown.data.model.City

data class AddState(
    val id: Int = 0,
    val code: String = "",
    val ciudadId: Int? = null,
    val person: String = "",
    val date: String = "",
    val description: String = "",
    val availableCities: List<City> = emptyList(),
    val isEditing: Boolean = false,
    val emptyFields: Boolean = false,
    val codeTooShort: Boolean = false,
    val codeExists: Boolean = false
)