package com.example.dinoappv2

import android.app.Application

class CustomApplication : Application() {
    //primitive int associated with image
    var widgetApplied: Int? = null

    //if image is badge or fb
    var widgetBadge: Boolean = false
}