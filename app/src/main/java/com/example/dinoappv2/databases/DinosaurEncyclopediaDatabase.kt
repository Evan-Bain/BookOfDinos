package com.example.dinoappv2.databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dinoappv2.dataClasses.DinosaurEncyclopediaTable

@Database(entities = [DinosaurEncyclopediaTable::class], version = 6, exportSchema = false)
abstract class DinosaurEncyclopediaDatabase : RoomDatabase() {

    abstract val dinosaurEncyclopediaDao: DinosaurEncyclopediaDao

    companion object {
        @Volatile
        private var INSTANCE: DinosaurEncyclopediaDatabase? = null

        fun getInstance(context: Context): DinosaurEncyclopediaDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DinosaurEncyclopediaDatabase::class.java,
                        "dinosaur_encyclopedia_database"
                    ).addCallback(object : RoomDatabase.Callback() {

                        //import the initial data into the database
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            for(i in 0..14) {
                                db.insert(
                                    "dinosaur_encyclopedia_table",
                                    SQLiteDatabase.CONFLICT_REPLACE,
                                    ContentValues().apply {
                                        put("position", i)
                                        put("activated", 0)
                                    }
                                )
                            }
                        }

                    }).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }

}