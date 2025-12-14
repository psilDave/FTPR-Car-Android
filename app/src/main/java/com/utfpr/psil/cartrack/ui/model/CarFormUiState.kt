package com.utfpr.psil.cartrack.ui.model

import com.google.android.gms.maps.model.LatLng

data class CarFormUiState(
    val carName: String = "",
    val carYear: String = "",
    val carPlate: String = "",
    val addressInput: String = "",
    val addressSuggestions: List<PlaceSuggestion> = emptyList(),
    val selectedCoordinates: LatLng? = null,
    val isLoadingSuggestions: Boolean = false,
    val carImageUrl: String = ""
)
