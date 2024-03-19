package com.example.dinoappv2.widget.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WidgetData::class], version = 1, exportSchema = false)
abstract class WidgetDataDatabase : RoomDatabase() {

    abstract val widgetImageDao: WidgetDataDao

    companion object {
        @Volatile
        private var INSTANCE: WidgetDataDatabase? = null

        fun getInstance(context: Context): WidgetDataDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WidgetDataDatabase::class.java,
                        "widget_data_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}