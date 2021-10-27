package com.example.dinoappv2.bottomNav

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinoappv2.DinoArticleActivity
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.EncyclopediaAdapter
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.FragmentEncyclopediaBinding
import com.example.dinoappv2.viewModels.BottomNavViewModel
import com.google.android.material.transition.MaterialFadeThrough


class EncyclopediaFragment : Fragment() {

    private val viewModel = BottomNavActivity.viewModel

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
        adapter =
            activity?.let { EncyclopediaAdapter(getAll() as ArrayList<DinosaurEncyclopedia>,
                it.applicationContext) }!!

        setHasOptionsMenu(true)

        //setting up recycler view
        binding.encyclopediaRecyclerView.adapter = adapter
        binding.encyclopediaRecyclerView.layoutManager = GridLayoutManager(context, 3)

        //when an image is clicked an activity opens
        adapter.positionClicked.observe(viewLifecycleOwner, {
            //allow the app to access what recycle view item was pressed
            CompanionObject.dinoArticleSelected = it
            //allow the app the access the data in DinosaurEncyclopedia
            CompanionObject.allDinos = viewModel.allDinos
            //tell viewModel what activity is being transitioned to
            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity, requireActivity().findViewById(R.id.dino_badge),
                "dino_badge_transition").toBundle()
            val intent = Intent(activity, DinoArticleActivity::class.java)
            startActivity(intent, options)
        })

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

        super.onCreateOptionsMenu(menu, inflater)
    }

}