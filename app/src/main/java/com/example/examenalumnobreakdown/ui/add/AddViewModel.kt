package com.example.examenalumnobreakdown.ui.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenalumnobreakdown.data.model.BreakDown
import com.example.examenalumnobreakdown.data.repository.BreakDownRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: BreakDownRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    var state by mutableStateOf(AddState())
        private set

    init {
        loadCities()
        val id: Int? = savedStateHandle["id"]
        if (id != null && id != -1) {
            loadBreakDown(id)
        }
    }

    private fun loadCities() {
        viewModelScope.launch {
            repository.getAllCities().collectLatest { cities ->
                state = state.copy(availableCities = cities)
            }
        }
    }

    private fun loadBreakDown(id: Int) {
        viewModelScope.launch {
            repository.getBreakDownById(id)?.let { breakDown ->
                state = state.copy(
                    id = breakDown.id,
                    code = breakDown.code,
                    ciudadId = breakDown.ciudadId,
                    person = breakDown.person,
                    date = breakDown.date,
                    description = breakDown.description,
                    isEditing = true
                )
            }
        }
    }

    fun onChangeCode(s: String){
        state = state.copy(code = s, codeExists = false, codeTooShort = false, emptyFields = false)
    }
    fun onChangeCityId(id: Int){
        state = state.copy(ciudadId = id, emptyFields = false)
    }
    fun onChangePerson(s: String){
        state = state.copy(person = s, emptyFields = false)
    }
    fun onChangeDate(s: String){
        state = state.copy(date = s, emptyFields = false)
    }
    fun onChangeDescription(s: String){
        state = state.copy(description = s)
    }

    fun clearErrors() {
        state = state.copy(emptyFields = false, codeTooShort = false, codeExists = false)
    }

    fun onSave(onSuccess: (String) -> Unit){
        viewModelScope.launch {
            // 1. Validación de campos vacíos (Punto 7.2 del examen)
            if (state.code.isBlank() || state.person.isBlank() || state.date.isBlank() || state.ciudadId == null) {
                state = state.copy(emptyFields = true)
                return@launch
            }

            // 2. Longitud del código > 3 (Punto 7.2 del examen)
            if (state.code.length <= 3) {
                state = state.copy(codeTooShort = true)
                return@launch
            }

            // 3. Verificación de existencia (Punto 7.1 del examen)
            // Solo si no estamos editando (si editamos, el código puede ser el mismo)
            if (!state.isEditing && repository.existsBreakDown(state.code)) {
                state = state.copy(codeExists = true)
                return@launch
            }

            val breakDown = BreakDown(
                id = if (state.isEditing) state.id else 0,
                code = state.code,
                ciudadId = state.ciudadId!!,
                person = state.person,
                date = state.date,
                description = state.description
            )

            if (state.isEditing) {
                repository.updateBreakDown(breakDown)
            } else {
                repository.insertBreakDown(breakDown)
            }

            onSuccess(state.code)
        }
    }
}