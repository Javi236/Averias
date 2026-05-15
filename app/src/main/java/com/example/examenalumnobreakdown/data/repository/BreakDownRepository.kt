package com.example.examenalumnobreakdown.data.repository

import com.example.examenalumnobreakdown.data.dao.BreakDownDao
import com.example.examenalumnobreakdown.data.dao.CityDao
import com.example.examenalumnobreakdown.data.model.BreakDown
import com.example.examenalumnobreakdown.data.model.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreakDownRepository @Inject constructor(
    private val breakDownDao: BreakDownDao,
    private val cityDao: CityDao
) {
    // Averías
    fun getAllBreakDowns() = breakDownDao.getAll()
    suspend fun insertBreakDown(breakDown: BreakDown) = breakDownDao.insert(breakDown)
    suspend fun updateBreakDown(breakDown: BreakDown) = breakDownDao.update(breakDown)
    suspend fun deleteBreakDown(breakDown: BreakDown) = breakDownDao.delete(breakDown)
    suspend fun existsBreakDown(code: String) = breakDownDao.exists(code)
    suspend fun getBreakDownById(id: Int) = breakDownDao.getById(id)

    // Ciudades
    fun getAllCities() = cityDao.getAll()
    suspend fun insertCity(city: City) = cityDao.insert(city)
    suspend fun updateCity(city: City) = cityDao.update(city)
    suspend fun deleteCity(city: City) = cityDao.delete(city)
}