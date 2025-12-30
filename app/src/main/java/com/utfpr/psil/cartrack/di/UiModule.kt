package com.utfpr.psil.cartrack.di

import com.utfpr.psil.cartrack.data.datasources.AuthDataSource
import com.utfpr.psil.cartrack.data.datasources.PlaceDataSource
import com.utfpr.psil.cartrack.data.repositories.AuthRepository
import com.utfpr.psil.cartrack.data.repositories.AuthRepositoryImpl
import com.utfpr.psil.cartrack.data.repositories.PlacesRepository
import com.utfpr.psil.cartrack.data.repositories.PlacesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UiModule {

    @Provides
    fun providesPlacesRepository(placeDataSource: PlaceDataSource): PlacesRepository =
        PlacesRepositoryImpl(placeDataSource)

    @Provides
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository =
        AuthRepositoryImpl(authDataSource)
}
