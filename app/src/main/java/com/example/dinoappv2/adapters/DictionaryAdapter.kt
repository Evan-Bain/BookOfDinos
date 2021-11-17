package com.example.dinoappv2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DictionaryStrings
import java.util.*
import kotlin.collections.ArrayList

class DictionaryAdapter(
    private val dictionaryData: ArrayList<DictionaryStrings>,
    private val context: Context
)
    : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>(), Filterable {

    private val words: List<DictionaryStrings> = ArrayList(dictionaryData)

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val wordTitle: TextView = view.findViewById(R.id.dictionary_recycler_word_title)
        val definition: TextView = view.findViewById(R.id.dictionary_recycler_definition)
        val title: TextView = view.findViewById(R.id.dictionary_title)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dictionary_recycler_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.wordTitle.text = dictionaryData[position].word
        holder.definition.text = dictionaryData[position].definition
    }

    override fun getItemCount() = dictionaryData.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<DictionaryStrings>()

                //if nothing entered in SearchView return all items
                if(constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(words)
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()

                    for(i in words) {
                        if(i.word.lowercase(Locale.getDefault())
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
                dictionaryData.clear()
                dictionaryData.addAll(results?.values as Collection<DictionaryStrings>)
                notifyDataSetChanged()
            }

        }
    }
}