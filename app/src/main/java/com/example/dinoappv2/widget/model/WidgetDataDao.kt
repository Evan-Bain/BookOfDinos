package com.example.dinoappv2.widget.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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