package com.example.examenalumnobreakdown.data.di

import android.content.Context
import com.example.examenalumnobreakdown.data.BreakDownDatabase
import com.example.examenalumnobreakdown.data.dao.BreakDownDao
import com.example.examenalumnobreakdown.data.dao.CityDao
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): BreakDownDatabase {
        return BreakDownDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideDao(database: BreakDownDatabase): BreakDownDao {
        return database.breakDownDao()
    }

    //Proveedor para el DAO de Ciudades
    @Provides
    @Singleton
    fun provideCityDao(database: BreakDownDatabase): CityDao {
        return database.cityDao()
    }
}