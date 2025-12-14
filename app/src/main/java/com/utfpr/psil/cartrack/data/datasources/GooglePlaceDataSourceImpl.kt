package com.utfpr.psil.cartrack.data.datasources

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.utfpr.psil.cartrack.ui.model.PlaceSuggestion
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GooglePlaceDataSourceImpl @Inject constructor(context: Context) : PlaceDataSource {
    private val placesClient = Places.createClient(context)

    override suspend fun getAutocompletePredictions(query: String): List<PlaceSuggestion> {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        return try {
            val response = placesClient.findAutocompletePredictions(request).await()
            response.autocompletePredictions.map { prediction ->
                PlaceSuggestion(
                    placeId = prediction.placeId,
                    description = prediction.getFullText(null).toString()
                )
            }
        } catch (e: Exception) {
            Log.e("GooglePlaceDataSource", "Error fetching autocomplete predictions", e)
            emptyList()
        }
    }

    override suspend fun fetchPlaceDetails(placeId: String): LatLng? {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.builder(placeId, placeFields).build()

        return try {
            val response = placesClient.fetchPlace(request).await()
            response.place.latLng
        } catch (e: Exception) {
            Log.e("GooglePlaceDataSource", "Error fetching place details", e)
            null
        }
    }
}