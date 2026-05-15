package com.example.examenalumnobreakdown.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "averia_table",
    foreignKeys = [
        ForeignKey(
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["ciudadId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BreakDown(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: String = "",
    val ciudadId: Int = 0,
    val person: String = "",
    val date: String = "",
    val description: String = ""
)