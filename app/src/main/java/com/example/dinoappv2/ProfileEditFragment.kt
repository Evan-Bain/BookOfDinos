package com.example.dinoappv2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dinoappv2.adapters.ProfileEditAdapter
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.ProfileImageDatabase
import com.example.dinoappv2.databinding.FragmentProfileEditBinding
import com.example.dinoappv2.viewModels.ProfileEditViewModel
import com.example.dinoappv2.viewModels.ProfileEditViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialSharedAxis

class ProfileEditFragment : Fragment() {

    lateinit var binding: FragmentProfileEditBinding

    lateinit var viewModel: ProfileEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile_edit,
            container,
            false
        )
        activity?.findViewById<MaterialToolbar>(R.id.bottom_nav_toolbar)?.setNavigationIcon(
            R.drawable.back_button
        )
        activity?.findViewById<MaterialToolbar>(R.id.bottom_nav_toolbar)?.setNavigationOnClickListener {
            activity?.findViewById<MaterialToolbar>(R.id.bottom_nav_toolbar)?.navigationIcon = null
            findNavController().navigateUp()
        }


        //get all badges that have been activated after quiz completion
        val dinoData = ArrayList<DinosaurEncyclopedia>()
        /*for(i in CompanionObject.allDinos!!) {
            if(i.activated == true) {
                dinoData.add(i)
            }
        }*/
        //setting up viewModel
        val database = ProfileImageDatabase.getInstance(requireContext()).profileImageDao
        val viewModelFactory = ProfileEditViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileEditViewModel::class.java)

        val adapter = ProfileEditAdapter(
            dinoData, requireContext(),
            viewModel.lastPositionClicked, viewModel.adapterRestarted
        )

        adapter.positionChanged.observe(viewLifecycleOwner) {
            //set to survive configuration changes
            viewModel.lastPositionClicked = adapter.lastPositionClicked
            if (adapter.currentSelected != null) {
                viewModel.currentPosition = adapter.currentSelected!!
            }
            //if there is a current position selected make
            //the select button visible
            binding.selectProfileImage.isEnabled =
                viewModel.currentPosition != -1
            //sets viewModel variable for the rest of lifecycle
            viewModel.adapterRestarted = true
        }

        //setting up recycler view
        binding.profileEditRecyclerView.adapter = adapter
        binding.profileEditRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3)

        //sets profile image to the position of the recycler view selected
        //and navigates back to the profile ui
        binding.selectProfileImage.setOnClickListener {
            val position = viewModel.currentPosition
            when {
                position > 0 -> {
                    viewModel.insertProfileImage(dinoData[position - 1].badge)
                }
                position == 0 -> {
                    viewModel.insertProfileImage(R.drawable.profile_icon)
                }
            }
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
            activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
                ?.visibility = View.VISIBLE
            activity?.findViewById<MaterialToolbar>(R.id.bottom_nav_toolbar)?.navigationIcon = null
            findNavController().navigate(ProfileEditFragmentDirections
                .actionProfileEditFragmentToProfileBottomNav())
        }
        return binding.root
    }

    //hides the bottom navigation view when fragment is started
    //put in onStart opposed to onCreate to keep hidden after leaving app
    //and coming back
    override fun onStart() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            ?.visibility = View.GONE
        super.onStart()
    }

}