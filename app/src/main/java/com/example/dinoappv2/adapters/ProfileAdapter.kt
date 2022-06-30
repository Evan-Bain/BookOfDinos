package com.example.dinoappv2.adapters

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.CustomApplication
import com.example.dinoappv2.R
import com.example.dinoappv2.WidgetProvider
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.WidgetData
import com.example.dinoappv2.databinding.ProfileRecyclerLayoutBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileAdapter(
    private val data: List<DinosaurEncyclopedia>,
    private val widgetData: List<WidgetData>,
    private val context: Context) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    //traditional recyclerView for optimization

    inner class ViewHolder(val binding: ProfileRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DinosaurEncyclopedia) {

            //sets image to grayscale if not unlocked
            //ADD FB IMAGES TOO FOR GRAYSCALE/COLOR
            with(binding) {
                dinoNameProfile.text =
                    context.resources.getStringArray(R.array.dinosaur_names_array)[data.position]

                if(data.activated) {
                    //dinoBadge image
                    dinoBadgeProfile.setImageResource(data.badge)
                    dinoBadgeProfileFab.setFab(data, true)

                    //Fb image
                    dinoFbProfile.setImageResource(data.dinosaurFb)
                    dinoFbProfileFab.setFab(data, false)
                } else {
                    //dinoBadge image
                    dinoBadgeProfile.setImageResource(data.blackBadge)
                    dinoBadgeProfileFab.visibility = View.GONE

                    //Fb image
                    dinoFbProfile.setImageResource(data.blackDinosaurFb)
                    dinoFbProfileFab.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProfileRecyclerLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    /** all logic to set up FAB as fully functioning and working with widgets **/
    //contains O(n)
    private fun FloatingActionButton.setFab(
        specificData: DinosaurEncyclopedia,
        dinoBadge: Boolean) {
        visibility = View.VISIBLE

        //determines if image is already added as a widget
        var associatedWidget: WidgetData? = null

        //search all widgets to determine if associated image is already a widget
        for(i in widgetData) {
            if(i.image == specificData.position && i.dinoBadge == dinoBadge) {
                associatedWidget = i
                break
            }
        }

        if(associatedWidget != null) {
            //image is already a widget (add: check mark, green background, & non-clickable)

            backgroundTintList =
                AppCompatResources.getColorStateList(context, R.color.primaryLightColor)
            setImageResource(R.drawable.check_icon)
            isClickable = false
        } else {
            //image is not a widget (add: add icon, secondary color background, & clickable)
            setOnClickListener { requestWidget(specificData.position, dinoBadge) }
            setImageResource(R.drawable.add_icon)
        }
    }

    /** display dialog (asking the user) requesting to place/create a widget for the
     *  associated image **/
    //NOTE: CHANGED FROM API 21 TO 26 FOR THIS CODE
    private fun requestWidget(selectedDino: Int, badge: Boolean) {

        //set app global variables to be accessed by WidgetProvider
        with((context.applicationContext as CustomApplication)) {
            widgetApplied = selectedDino
            widgetBadge = badge
        }

        val appWidgetManager: AppWidgetManager? =
            ContextCompat.getSystemService(context, AppWidgetManager::class.java)

        //get associated widget size for badge or fb
        val widgetProvider = if(badge) {
            ComponentName(context, WidgetProvider.BadgeWidgetProvider::class.java)
        } else {
            ComponentName(context, WidgetProvider.FbWidgetProvider::class.java)
        }

        //display dialog to request to create/place widget
        if (appWidgetManager!!.isRequestPinAppWidgetSupported) {
            val pinnedWidgetCallbackIntent =
                Intent(context, WidgetProvider::class.java)
            val successCallback = PendingIntent.getBroadcast(
                context, 0,
                pinnedWidgetCallbackIntent, PendingIntent.FLAG_IMMUTABLE
            )
            appWidgetManager.requestPinAppWidget(widgetProvider, Bundle(), successCallback)
        }
    }
    //NOTE: CHANGED FROM API 21 TO 26 FOR THIS CODE
}

