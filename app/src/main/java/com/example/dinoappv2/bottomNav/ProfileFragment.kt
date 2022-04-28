package com.example.dinoappv2.bottomNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinoappv2.BottomNavRepository
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.ProfileAdapter
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databinding.FragmentProfileBinding
import com.example.dinoappv2.viewModels.EncyclopediaViewModel
import com.example.dinoappv2.viewModels.EncyclopediaViewModelFactory
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.Dispatchers

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: EncyclopediaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        val dinoDatasource = DinosaurEncyclopediaDatabase.getInstance(requireContext())
            .dinosaurEncyclopediaDao
        val bottomNavRepository = BottomNavRepository(dinoDatasource, Dispatchers.IO)
        val viewModelFactory = EncyclopediaViewModelFactory(bottomNavRepository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(EncyclopediaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        if(findNavController().previousBackStackEntry?.destination?.id == R.id.profile_edit_fragment) {
            enterTransition = MaterialFadeThrough()
            exitTransition = MaterialFadeThrough()
        }

        binding.profileBadgesRecycler.layoutManager =
            GridLayoutManager(requireActivity(), 3)

        //gets all activated badges
        val dinoData = ArrayList<DinosaurEncyclopedia>()
        viewModel.allDinos.observe(viewLifecycleOwner) {

            binding.profileBadgesRecycler.adapter = ProfileAdapter(it)

            for(i in it) {
                if(i.activated == true) {
                    dinoData.add(i)
                }
            }
        }

        //when fab selected navigate to the profile edit screen
        binding.editProfileImageFab.setOnClickListener {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)
            findNavController().navigate(ProfileFragmentDirections
                .actionProfileBottomNavToProfileEditFragment())
        }

        //set profile image to the currently selected one
        /*lifecycleScope.launchWhenCreated {
            binding.profileImage.setImageResource(
                viewModel.profileDatabase.getImage()
            )
        }*/

        //sets experience count
        /*binding.experienceRatio.text =
            resources.getString(R.string.experience_fraction,dinoData.size*10)*/

        return binding.root
    }
}