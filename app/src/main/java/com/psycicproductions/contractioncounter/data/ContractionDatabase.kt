package com.psycicproductions.contractioncounter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Contraction::class],
    version = 1,
    exportSchema = false
)
abstract class ContractionDatabase : RoomDatabase() {
    abstract fun contractionDao(): ContractionDao

    companion object {
        @Volatile
        private var INSTANCE: ContractionDatabase? = null

        fun getDatabase(context: Context): ContractionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContractionDatabase::class.java,
                    "contraction_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}