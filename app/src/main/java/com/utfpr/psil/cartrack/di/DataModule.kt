package com.utfpr.psil.cartrack.di

import android.content.Context
import com.utfpr.psil.cartrack.data.datasources.AuthDataSource
import com.utfpr.psil.cartrack.data.datasources.GooglePlaceDataSourceImpl
import com.utfpr.psil.cartrack.data.datasources.PlaceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesGooglePlaceDataSource(@ApplicationContext context: Context): PlaceDataSource =
        GooglePlaceDataSourceImpl(context)

    @Provides
    @Singleton
    fun providesAuthDataSource(): AuthDataSource = AuthDataSource()
}