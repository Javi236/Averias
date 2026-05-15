package com.example.examenalumnobreakdown.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.examenalumnobreakdown.data.converter.Converters
import com.example.examenalumnobreakdown.data.dao.BreakDownDao
import com.example.examenalumnobreakdown.data.dao.CityDao
import com.example.examenalumnobreakdown.data.model.BreakDown
import com.example.examenalumnobreakdown.data.model.City
import java.util.concurrent.Executors

@Database(
    version = 4,
    entities = [BreakDown::class, City::class], // Añadimos la nueva entidad City
    exportSchema = false
)
@TypeConverters(
    Converters::class,
)
abstract class BreakDownDatabase : RoomDatabase() {

    abstract fun breakDownDao(): BreakDownDao
    abstract fun cityDao(): CityDao // Añadimos el nuevo DAO

    companion object {
        @Volatile
        private var INSTANCE: BreakDownDatabase? = null

        fun getDatabase(context: Context): BreakDownDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BreakDownDatabase::class.java,
                    "breakdownapp.db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        @RequiresApi(Build.VERSION_CODES.O)
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { database ->
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}