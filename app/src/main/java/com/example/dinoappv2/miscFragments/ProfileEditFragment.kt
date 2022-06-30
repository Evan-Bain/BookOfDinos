package com.example.dinoappv2.miscFragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.ProfileEditAdapter
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.ProfileImageDatabase
import com.example.dinoappv2.databinding.FragmentProfileEditBinding
import com.example.dinoappv2.viewModels.ProfileEditViewModel
import com.example.dinoappv2.viewModels.ProfileEditViewModelFactory
import com.google.android.material.transition.MaterialSharedAxis

class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var viewModel: ProfileEditViewModel
    private lateinit var adapter: ProfileEditAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting up viewModel
        val database = ProfileImageDatabase.getInstance(requireContext()).profileImageDao
        val viewModelFactory = ProfileEditViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileEditViewModel::class.java]

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
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

        ViewCompat.setNestedScrollingEnabled(binding.profileEditRecyclerView, false)

        viewModel.setDinoSelected(false)
        viewModel.dinoSelected.observe(viewLifecycleOwner) {
            if(it) {
                fadeIn(binding.selectProfileImage)
            } else {
                fadeOut(binding.selectProfileImage)
            }
        }

        @Suppress("UNCHECKED_CAST")
        val dinoData = arguments?.get("activatedDinos") as ArrayList<DinosaurEncyclopedia>

        adapter = ProfileEditAdapter { dino, selected ->
            viewModel.setCurrentDino(dino)
            viewModel.setDinoSelected(selected)
        }
        adapter.submitList(dinoData.filter { it.activated })

        binding.profileEditRecyclerView.adapter = adapter

        //sets profile image to the position of the recycler view selected
        //and navigates back to the profile ui
        binding.selectProfileImage.setOnClickListener {
            viewModel.currentDino?.let {
                viewModel.insertProfileImage(it.position)
                findNavController().navigate(ProfileEditFragmentDirections
                    .actionProfileEditFragmentToProfileBottomNav())
            }
        }
        return binding.root
    }

    private fun fadeIn(button: Button) {
        button.isClickable = true
        ObjectAnimator.ofFloat(button, "alpha", 1f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun fadeOut(button: Button) {
        button.isClickable = false
        ObjectAnimator.ofFloat(button, "alpha", 0f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}