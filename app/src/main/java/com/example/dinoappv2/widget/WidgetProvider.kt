package com.example.dinoappv2.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.dinoappv2.CustomApplication
import com.example.dinoappv2.R
import com.example.dinoappv2.widget.model.WidgetData
import com.example.dinoappv2.widget.model.WidgetDataDao
import com.example.dinoappv2.widget.model.WidgetDataDatabase
import com.example.dinoappv2.widget.model.toResourceId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//lol this file is hell

/**
 * Implementation of App Widget functionality.
 */
abstract class WidgetProvider : AppWidgetProvider() {

    var database: WidgetDataDao? = null
    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        database = WidgetDataDatabase.getInstance(context).widgetImageDao

        //TESTING PURPOSES
        /*scope.launch {
            backgroundDatabase!!.deleteAll()
        }*/
        //TESTING PURPOSES

        //add widget to WidgetDatabase
        addAppWidget(context, appWidgetIds[0])

        //ran everytime WidgetDatabase is modified
        //contains O(n)
        scope.launch {
            database?.getWidgets()?.collect { newValue ->
                for (i in newValue) {
                    //set every widget on home screen to latest data
                    updateAppWidget(context, appWidgetManager, i.widgetId, i)
                }
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        //when pendingIntent was sent (user placed/created widget from dialog)
        //navigate to home screen (should show where widget is placed)
        Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)

            //specifies this operation is on purpose (animation added to justify)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(this)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray?) {
        database = WidgetDataDatabase.getInstance(context).widgetImageDao

        //delete corresponding widget from WidgetDatabase
        //(signals app that widget is no longer on home screen)
        scope.launch {
            appWidgetIds?.forEach {
                database!!.deleteWidget(it)
            }
        }
    }

    /** adds widget to WidgetDatabase with corresponding data **/
    private fun addAppWidget(
        context: Context,
        appWidgetId: Int,
    ) {
        with((context.applicationContext as CustomApplication)) {
            widgetApplied?.let {
                scope.launch {
                    database?.insert(WidgetData(appWidgetId, widgetBadge, it))
                }
            }
        }
    }

    /** displays widget to the home screen **/
    //set abstract for two sizes of widgets (badge & fb)
    abstract fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        data: WidgetData
    )

    class BadgeWidgetProvider : WidgetProvider() {
        override fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            data: WidgetData
        ) {

            //resource id of image from corresponding primitive int
            val dinoImage = with(data) {
                image.toResourceId(dinoBadge)
            }

            //set widget on the home screen with associated image
            val views = RemoteViews(context.packageName, R.layout.badge_widget)
            views.setImageViewResource(R.id.appwidget_badge, dinoImage)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    class FbWidgetProvider : WidgetProvider() {
        override fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            data: WidgetData
        ) {

            //resource id of image from corresponding primitive int
            val dinoImage = with(data) {
                image.toResourceId(dinoBadge)
            }

            //set widget on the home screen with associated image
            val views = RemoteViews(context.packageName, R.layout.fb_widget)
            views.setImageViewResource(R.id.appwidget_fb, dinoImage)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}