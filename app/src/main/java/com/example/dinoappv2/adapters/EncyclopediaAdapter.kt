package com.example.dinoappv2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import com.example.dinoappv2.viewModels.BottomNavViewModel
import java.util.*
import kotlin.collections.ArrayList

class EncyclopediaAdapter(private val dinoData: ArrayList<DinosaurEncyclopedia>,
                          private val context: Context):
    RecyclerView.Adapter<EncyclopediaAdapter.ViewHolder>(), Filterable {

    //variable used to filter results in SearchView
    var dinos: List<DinosaurEncyclopedia> = ArrayList(dinoData)

    //holds value of the position of a view
    private val _positionClicked = MutableLiveData<Int>()
    val positionClicked: LiveData<Int>
        get() = _positionClicked

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.dino_name)
        val imageView: ImageView = view.findViewById(R.id.dino_badge)
        val check: ImageView = view.findViewById(R.id.dino_badge_check)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.encyclopedia_recycler_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = context.getString(dinoData[position].dinosaurKey)
        holder.imageView.setImageResource(dinoData[position].badge)
        if(dinoData[position].activated) {
            holder.check.visibility = View.VISIBLE
        }
        //when the image is clicked the positionClicked livedata changes to the position
        //that was clicked
        holder.imageView.setOnClickListener {
            _positionClicked.value = position
        }
    }

    override fun getItemCount(): Int = dinoData.size

    //filter for SearchView
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<DinosaurEncyclopedia>()

                //if nothing entered in SearchView return all items
                if(constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(dinos)
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()

                    for(i in dinos) {
                        if(context.getString(i.dinosaurKey).lowercase(Locale.getDefault())
                                .startsWith(filterPattern)) {
                            filteredList.add(i)
                        }
                    }
                }

                val results = object : FilterResults(){}
                results.values = filteredList

                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dinoData.clear()
                dinoData.addAll(results?.values as Collection<DinosaurEncyclopedia>)
                notifyDataSetChanged()
            }

        }
    }


}