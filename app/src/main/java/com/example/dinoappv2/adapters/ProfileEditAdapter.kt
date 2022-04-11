package com.example.dinoappv2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia

class ProfileEditAdapter(
    private val dinoData: List<DinosaurEncyclopedia>,
    private val context: Context,
    val lastPositionClicked: ArrayList<ViewHolder> = ArrayList(),
    adapterRestarted: Boolean
): RecyclerView.Adapter<ProfileEditAdapter.ViewHolder>() {

    //lets activity know if a new position has been clicked
    private val _positionChanged = MutableLiveData<Boolean>()
    val positionChanged: LiveData<Boolean>
        get() = _positionChanged

    //the currently selected position
    var currentSelected: Int? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.profile_edit_badge)
        val textView: TextView = view.findViewById(R.id.profile_edit_name)
        val check: ImageView = view.findViewById(R.id.profile_edit_check)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.profile_edit_recycler_layout, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //if the position is at 0 set image to default profile image
        //otherwise set badges activated
        if(position > 0) {
            holder.imageView.setImageResource(dinoData[position - 1].badge)
            holder.textView.text = dinoData[position - 1].name
        } else {
            holder.imageView.setImageResource(R.drawable.profile_icon)
            holder.textView.text = context.getText(R.string.profile_default)
        }

        //sets the ui the same as it was before configuration change
        if(lastPositionClicked.size > 0) {
            if(lastPositionClicked[lastPositionClicked.size - 1].adapterPosition
                == holder.adapterPosition) {
                holder.check.visibility = lastPositionClicked[lastPositionClicked.size - 1]
                    .check.visibility
                lastPositionClicked.add(holder)
            }
        }

        holder.imageView.setOnClickListener {
            val currentPosition = holder.adapterPosition
            //check is there was a position clicked
            if(lastPositionClicked.size > 0) {
                val lastPosition =
                    lastPositionClicked[lastPositionClicked.size - 1]
                //lets user select and reselect same image
                if(holder.check.visibility == View.GONE
                    && lastPosition.adapterPosition == currentPosition) {
                    holder.check.visibility = View.VISIBLE
                    currentSelected = currentPosition
                } else {
                    //deletes previous check marks and adds it to the new position selected
                    holder.check.visibility = View.VISIBLE
                    currentSelected = currentPosition
                    lastPosition.check.visibility = View.GONE
                    //sets no position selected if the view is not visible
                    if(holder.check.visibility == View.GONE) {
                        currentSelected = -1
                    }
                }
            } else {
                holder.check.visibility = View.VISIBLE
                currentSelected = currentPosition
            }
            //add the position that was just clicked to the top of the array
            lastPositionClicked.add(holder)
            //notify activity that a change has been made
            _positionChanged.value = !positionChanged.value!!
        }

    }

    override fun getItemCount() = dinoData.size + 1

    init {
        _positionChanged.value = false
        //if this is the first time the adapter is being created
        //and not just a configuration change set no position
        if(!adapterRestarted) {
            currentSelected = -1
        }
    }
}