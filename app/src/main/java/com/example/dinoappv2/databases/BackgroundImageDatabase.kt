package com.example.dinoappv2.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dinoappv2.dataClasses.BackgroundImage

@Database(entities = [BackgroundImage::class], version = 1, exportSchema = false)
abstract class BackgroundImageDatabase: RoomDatabase() {
    abstract val backgroundImageDao: BackgroundImageDao

    companion object {
        @Volatile
        private var INSTANCE: BackgroundImageDatabase? = null

        fun getInstance(context: Context): BackgroundImageDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BackgroundImageDatabase::class.java,
                        "background_image_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}