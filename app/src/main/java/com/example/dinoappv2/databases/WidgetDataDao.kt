package com.example.dinoappv2.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dinoappv2.dataClasses.WidgetData
import kotlinx.coroutines.flow.Flow

@Dao
interface WidgetDataDao {

    @Insert
    suspend fun insert(data: WidgetData)

    @Query("SELECT * FROM widget_data_table WHERE :id = widgetId")
    fun getWidget(id: Int): Flow<WidgetData>

    @Query("SELECT * FROM widget_data_table")
    fun getWidgets(): Flow<List<WidgetData>>

    //USED FOR TESTING
    @Query("DELETE FROM widget_data_table")
    suspend fun deleteAll()
    //USED FOR TESTING

    @Query("DELETE FROM widget_data_table WHERE :id = widgetId")
    suspend fun deleteWidget(id: Int)
}