package com.example.examenalumnobreakdown.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.examenalumnobreakdown.data.model.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert
    suspend fun insert(city: City)

    @Update
    suspend fun update(city: City)

    @Delete
    suspend fun delete(city: City)

    @Query("SELECT * FROM ciudad_table")
    fun getAll(): Flow<List<City>>
}