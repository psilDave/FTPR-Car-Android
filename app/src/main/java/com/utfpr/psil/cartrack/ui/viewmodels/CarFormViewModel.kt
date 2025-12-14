package com.utfpr.psil.cartrack.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utfpr.psil.cartrack.data.repositories.PlacesRepository
import com.utfpr.psil.cartrack.ui.model.CarFormUiState
import com.utfpr.psil.cartrack.ui.model.PlaceSuggestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarFormViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarFormUiState())
    val uiState: StateFlow<CarFormUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onAddressInputChange(query: String) {
        searchJob?.cancel()

        _uiState.update { it.copy(addressInput = query) }
        if (query.length < 2) {
            _uiState.update { it.copy(addressSuggestions = emptyList()) }
            return
        }
        searchJob = viewModelScope.launch {
            delay(300L)
            _uiState.update { it.copy(isLoadingSuggestions = true) }
            try {
                val suggestions = placesRepository.getAutocompleteSuggestions(query)
                _uiState.update {
                    it.copy(
                        addressSuggestions = suggestions,
                        isLoadingSuggestions = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoadingSuggestions = false) }
            }
        }
    }

    fun onSuggestionSelected(suggestion: PlaceSuggestion) {
        _uiState.update {
            it.copy(
                addressInput = suggestion.description,
                addressSuggestions = emptyList()
            )
        }
        viewModelScope.launch {
            val coordinates = placesRepository.getCoordinatesForPlace(suggestion.placeId)
            _uiState.update { it.copy(selectedCoordinates = coordinates) }
        }
    }

    fun onCarNameChange(name: String) = _uiState.update { it.copy(carName = name) }
    fun onCarYearChange(year: String) = _uiState.update { it.copy(carYear = year) }
    fun onCarPlateChange(plate: String) = _uiState.update { it.copy(carPlate = plate) }
    fun onDismissSuggestions() = _uiState.update { it.copy(addressSuggestions = emptyList()) }

}