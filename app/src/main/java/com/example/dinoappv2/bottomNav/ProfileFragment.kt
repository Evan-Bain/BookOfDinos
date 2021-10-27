package com.example.dinoappv2.bottomNav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinoappv2.ProfileEditFragment
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.ProfileAdapter
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databinding.FragmentProfileBinding
import com.example.dinoappv2.viewModels.BottomNavViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: BottomNavViewModel

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
            R.layout.fragment_profile,
            container,
            false
        )
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            ?.visibility = View.VISIBLE
        viewModel = BottomNavActivity.viewModel
        binding.profileBadgesRecycler.adapter = ProfileAdapter(getDatabase())
        binding.profileBadgesRecycler.layoutManager =
            GridLayoutManager(requireActivity(), 3)

        //set profile image to the currently selected one
        lifecycleScope.launchWhenCreated {
            binding.profileImage.setImageResource(
                viewModel.profileDatabase.getImage()
            )
        }

        //gets all activated badges
        val dinoData = ArrayList<DinosaurEncyclopedia>()
        for(i in CompanionObject.allDinos!!) {
            if(i.activated) {
                dinoData.add(i)
            }
        }

        //sets experience count
        binding.experienceRatio.text =
            resources.getString(R.string.experience_fraction,dinoData.size*10)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //when fab selected navigate to the profile edit screen
        binding.editProfileImageFab.setOnClickListener {
            enterTransition =
                MaterialSharedAxis(MaterialSharedAxis.Z, false)
            exitTransition =
                MaterialSharedAxis(MaterialSharedAxis.Z,true)
            reenterTransition =
                MaterialSharedAxis(MaterialSharedAxis.Z,false)
            findNavController().navigate(ProfileFragmentDirections
                .actionProfileBottomNavToProfileEditFragment())
        }
    }

    private fun getDatabase(): List<DinosaurEncyclopedia> {
        return viewModel.allDinos
    }
}