package com.example.dinoappv2.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia

@Database(entities = [DinosaurEncyclopedia::class], version = 5, exportSchema = false)
abstract class DinosaurEncyclopediaDatabase: RoomDatabase() {

    abstract val dinosaurEncyclopediaDao: DinosaurEncyclopediaDao

    companion object {
        @Volatile
        private var INSTANCE: DinosaurEncyclopediaDatabase? = null

        fun getInstance(context: Context): DinosaurEncyclopediaDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DinosaurEncyclopediaDatabase::class.java,
                        "dinosaur_encyclopedia_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }

}