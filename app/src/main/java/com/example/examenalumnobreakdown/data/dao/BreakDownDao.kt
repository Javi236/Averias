package com.example.examenalumnobreakdown.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.examenalumnobreakdown.data.model.BreakDown
import kotlinx.coroutines.flow.Flow

@Dao
interface BreakDownDao {
    @Insert
    suspend fun insert(breakDown: BreakDown)

    @Update
    suspend fun update(breakDown: BreakDown)

    @Delete
    suspend fun delete(breakDown: BreakDown)

    @Query("SELECT * FROM averia_table")
    fun getAll(): Flow<List<BreakDown>>

    @Query("SELECT EXISTS(SELECT * FROM averia_table WHERE code = :code)")
    suspend fun exists(code: String): Boolean

    @Query("SELECT * FROM averia_table WHERE id = :id")
    suspend fun getById(id: Int): BreakDown?
}