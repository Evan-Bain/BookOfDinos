package com.example.dinoappv2.bottomNav

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.DictionaryAdapter
import com.example.dinoappv2.databinding.FragmentDictionaryBinding
import com.example.dinoappv2.viewModels.DictionaryViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding

    private val viewModel: DictionaryViewModel by viewModels()

    private val adapter = DictionaryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //if transitioned from DinoArticle (via clicked "difficult word" display slide right
        //transition else perform normal fade transition
        if(findNavController().previousBackStackEntry?.destination?.id == R.id.dino_article_fragment) {
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
                duration = 750
            }
        } else {
            enterTransition = MaterialFadeThrough()
        }
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

        //Disable nested scrolling to not activate collapsable toolbar
        ViewCompat.setNestedScrollingEnabled(binding.recyclerViewDictionary, false)

        //setting up recycler view
        binding.recyclerViewDictionary.adapter = adapter
        binding.recyclerViewDictionary.layoutManager = LinearLayoutManager(requireContext())

        //Disable automatic ListAdapter animations if transitioned from DinoArticle to avoid
        //unnecessary motion
        val argWord = arguments?.get("selectedWord")
        if(argWord != null) {
            viewModel.filterDictionaryData(argWord as String)
            binding.recyclerViewDictionary.itemAnimator = null
        }

        viewModel.allWords.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    //options menu is the searchView
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_options_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search_menu_item)
        val searchView: SearchView = searchItem.actionView as SearchView
        //adds hint to say what to type
        searchView.queryHint = "Search words"

        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                //if animations are off turn on
                if(binding.recyclerViewDictionary.itemAnimator == null) {
                    binding.recyclerViewDictionary.itemAnimator = DefaultItemAnimator()
                }
                //hides bottom nav if searching for a word
                activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                //makes bottom nav visible again if done searching for a word
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
                viewModel.filterDictionaryData(newText)
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }
}