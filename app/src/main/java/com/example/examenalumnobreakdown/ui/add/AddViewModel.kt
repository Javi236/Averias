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
    var exists by mutableStateOf(false)
        private set
    var menor by mutableStateOf(false)
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
        state = state.copy(code = s)
    }
    fun onChangeCityId(id: Int){
        state = state.copy(ciudadId = id)
    }
    fun onChangePerson(s: String){
        state = state.copy(person = s)
    }
    fun onChangeDate(s: String){
        state = state.copy(date = s)
    }
    fun onChangeDescription(s: String){
        state = state.copy(description = s)
    }

    fun onSave(onSuccess: () -> Unit){
        viewModelScope.launch {
            if(state.code.length < 3){
                menor = true
                return@launch
            }
            
            if (state.ciudadId == null) {
                // Validación básica de ciudad
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

            // Solo verificamos si existe el código si estamos CREANDO una nueva
            if(!state.isEditing && repository.existsBreakDown(state.code)) {
                exists = true
            } else {
                if (state.isEditing) {
                    repository.updateBreakDown(breakDown)
                } else {
                    repository.insertBreakDown(breakDown)
                }
                exists = false
                onSuccess()
            }
        }
    }
}