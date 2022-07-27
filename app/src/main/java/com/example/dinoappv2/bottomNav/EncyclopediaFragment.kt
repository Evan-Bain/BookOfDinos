package com.example.dinoappv2.bottomNav

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.EncyclopediaAdapter
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.FragmentEncyclopediaBinding
import com.example.dinoappv2.viewModels.EncyclopediaViewModel
import com.example.dinoappv2.viewModels.EncyclopediaViewModelFactory
import com.example.dinoappv2.viewModels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis


class EncyclopediaFragment : Fragment() {

    private lateinit var binding: FragmentEncyclopediaBinding

    private lateinit var viewModel: EncyclopediaViewModel
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()

        val viewModelFactory =
            EncyclopediaViewModelFactory(resources.getStringArray(R.array.dinosaur_names_array))
        viewModel = ViewModelProvider(this, viewModelFactory)[
                EncyclopediaViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_encyclopedia,
            container,
            false
        )

        //Disable nested scrolling to not activate collapsable toolbar
        ViewCompat.setNestedScrollingEnabled(binding.encyclopediaRecyclerView, false)

        //Re-enable normal transitions after navigating away from DinoArticle
        if(findNavController().previousBackStackEntry?.destination?.id == R.id.dino_article_fragment) {
            enterTransition = MaterialFadeThrough()
            exitTransition = MaterialFadeThrough()
            reenterTransition = MaterialFadeThrough()
        }

        //setting up recyclerView
        val adapter = EncyclopediaAdapter(this) { model ->
            //pass click listener
            model.onBadgeClicked()
        }
        binding.encyclopediaRecyclerView.adapter = adapter
        binding.encyclopediaRecyclerView.layoutManager = GridLayoutManager(context, 3)

        viewModel.setDinoData(sharedViewModel.dinosaurData.value!!)
        viewModel.filteredDinos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    //creating SearchView widget
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_options_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search_menu_item)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search dinosaurs"

        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                //disable bottomNav if search is being performed
                activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                //enable bottomNav if search is done being performed
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
                viewModel.filterDinosaurData(newText)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    /** logic for transiting to DinoArticleFragment **/
    private fun DinosaurEncyclopedia.onBadgeClicked() {
        //change transitions before entering DinoArticle to a Z axis transition
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        //Pass dinosaur clicked to DinoArticle
        val bundle = bundleOf("dinoSelected" to this)
        findNavController().navigate(R.id.dino_article_fragment, bundle)
    }
}