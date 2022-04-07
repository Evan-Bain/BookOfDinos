package com.example.dinoappv2.bottomNav

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinoappv2.DinoArticleActivity
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.EncyclopediaAdapter
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.FragmentEncyclopediaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialFadeThrough


class EncyclopediaFragment : Fragment() {

    private val viewModel = BottomNavActivity.viewModel

    private val dinosaurData = getAll()
    private val dinosaurNames: MutableList<String> = mutableListOf()

    private lateinit var adapter: EncyclopediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentEncyclopediaBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_encyclopedia,
            container,
            false
        )

        adapter = EncyclopediaAdapter(requireContext()) { model, imageView ->
            model.onBadgeClicked(imageView)
        }
        adapter.submitList(dinosaurData)

        setHasOptionsMenu(true)

        //setting up recycler view
        binding.encyclopediaRecyclerView.adapter = adapter
        binding.encyclopediaRecyclerView.layoutManager = GridLayoutManager(context, 3)

        //extracting dinosaur names so SearchView can search through the names
        for(i in dinosaurData) {
            dinosaurNames.add(requireContext().getString(i.dinosaurKey))
        }

        return binding.root
    }

    //gets reference of all entities of each dinosaur
    private fun getAll(): List<DinosaurEncyclopedia> {
        return viewModel.allDinos
    }

    //creating SearchView widget
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_options_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search_menu_item)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = "Search dinosaurs"

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
                adapter.submitList(filterDinosaurData(newText))
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    fun filterDinosaurData(text: String?): List<DinosaurEncyclopedia> {

        //if there is nothing typed in display all the dinos
        if(text == null || text == "") {
            return dinosaurData
        }

        //return the dinos that match the start of the inputted text (not case sensitive)
        val length = text.length
        val newDinosaurData: MutableList<DinosaurEncyclopedia> = mutableListOf()
        for((i, name) in dinosaurNames.withIndex()) {
            if(name.take(length).lowercase() == text) {
                newDinosaurData.add(dinosaurData[i])
            }
        }
        return newDinosaurData
    }

    fun DinosaurEncyclopedia.onBadgeClicked(dinoBadge: ImageView) {
        //allow the app to access what recycle view item was pressed
        CompanionObject.dinoArticleSelected = this.position
        //allow the app the access the data in DinosaurEncyclopedia
        CompanionObject.allDinos = viewModel.allDinos
        //tell viewModel what activity is being transitioned to
        val options = ActivityOptions.makeSceneTransitionAnimation(
            activity, dinoBadge,
            "dino_badge_transition").toBundle()
        val intent = Intent(activity, DinoArticleActivity::class.java)
        startActivity(intent, options)
    }
}