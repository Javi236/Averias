package com.example.examenalumnobreakdown.data.converter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Converters {

    @TypeConverter
    fun fromJavaLocalDate(date: LocalDate?): String? {
        // Usamos el formateador nativo de Java
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun toJavaLocalDate(dateString: String?): LocalDate? {
        return dateString?.let {
            java.time.LocalDate.parse(it,DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

}