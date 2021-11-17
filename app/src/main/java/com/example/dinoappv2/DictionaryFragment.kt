package com.example.dinoappv2

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinoappv2.adapters.DictionaryAdapter
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DictionaryStrings
import com.example.dinoappv2.databinding.FragmentDictionaryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding

    private lateinit var adapter: DictionaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dictionary,
            container,
            false
        )
        DictionaryStrings("","").addStrings()
        adapter = DictionaryAdapter(
            DictionaryStrings.dictionaryStrings,
            requireContext()
        )
        binding.recyclerViewDictionary.adapter = adapter
        binding.recyclerViewDictionary.layoutManager = LinearLayoutManager(requireContext())
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_options_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search_menu_item)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = "search..."
        searchView.setIconifiedByDefault(false)

        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.VISIBLE
                return true
            }

        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            //adding filter to SearchView
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        if(CompanionObject.wordClicked != null) {
            searchItem.expandActionView()
            searchView.setQuery(CompanionObject.wordClicked, true)
            searchView.clearFocus()
            CompanionObject.wordClicked = null
        }

        super.onCreateOptionsMenu(menu, inflater)
    }
}