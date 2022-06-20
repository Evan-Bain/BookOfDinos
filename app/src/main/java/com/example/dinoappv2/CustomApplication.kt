package com.example.dinoappv2

import android.app.Application
import com.example.dinoappv2.dataClasses.WidgetData
import com.example.dinoappv2.databases.WidgetDataDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomApplication : Application() {
    //primitive int associated with image
    var widgetApplied: Int? = null

    //if image is badge or fb
    var widgetBadge: Boolean = false
}