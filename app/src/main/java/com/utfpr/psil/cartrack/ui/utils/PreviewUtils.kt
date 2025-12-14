package com.utfpr.psil.cartrack.ui.utils

import android.content.Context
import com.utfpr.psil.cartrack.data.datasources.GooglePlaceDataSourceImpl
import com.utfpr.psil.cartrack.data.repositories.PlacesRepositoryImpl
import com.utfpr.psil.cartrack.ui.viewmodels.CarFormViewModel

fun carFormViewModel(context: Context) = CarFormViewModel(
    placesRepository = PlacesRepositoryImpl(
        GooglePlaceDataSourceImpl(context)
    )
)