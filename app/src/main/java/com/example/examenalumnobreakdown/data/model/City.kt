package com.example.examenalumnobreakdown.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ciudad_table")
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val cp: String,
    val poblacion: Int
)