package com.example.dinoappv2.bottomNav

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.adapters.ProfileAdapter
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.ProfileImageDatabase
import com.example.dinoappv2.databases.WidgetDataDatabase
import com.example.dinoappv2.databinding.FragmentProfileBinding
import com.example.dinoappv2.viewModels.MainViewModel
import com.example.dinoappv2.viewModels.ProfileViewModel
import com.example.dinoappv2.viewModels.ProfileViewModelFactory
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    //viewModel shared from the MainActivity
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()

        val profileDataSource = ProfileImageDatabase.getInstance(requireContext())
            .profileImageDao
        val widgetDataSource = WidgetDataDatabase.getInstance(requireContext()).widgetImageDao
        val viewModelFactory = ProfileViewModelFactory(profileDataSource, widgetDataSource)

        //initializing ProfileViewModel
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //recycleView is not altered (improves performance)
        binding.profileBadgesRecycler.setHasFixedSize(true)

        sharedViewModel.dinosaurData.observe(viewLifecycleOwner) {
            viewModel.setAllDinos(it)
        }

        //re-enable normal transitions after navigating away from ProfileEditFragment
        if(findNavController().previousBackStackEntry?.destination?.id == R.id.profile_edit_fragment) {
            enterTransition = MaterialFadeThrough()
            exitTransition = MaterialFadeThrough()
            reenterTransition = MaterialFadeThrough()
        }

        lifecycleScope.launch {
            sharedViewModel.backgroundImage.apply {
                if(this != null) {
                    //sets corresponding background preview depending on what background is
                    //saved in BackgroundImageDatabase
                    binding.backgroundImage.setImageResource(
                        when(backgroundImage) {
                            0 -> R.drawable.background_land
                            1 -> R.drawable.background_water
                            2 -> R.drawable.background_sky
                            else -> {
                                binding.backgroundImage.visibility = View.GONE
                                0
                            }
                        }
                    )
                } else {
                    //displays default background preview if background has not been selected by
                    //user yet
                    binding.backgroundImage.visibility = View.GONE
                }
            }
        }

        //navigates to SelectBackgroundImage when background image preview is selected
        binding.backgroundImagePreview.setOnClickListener {

            //change transition from MaterialFade
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)

            findNavController().navigate(R.id.select_background_fragment)
        }

        with(viewModel) {
            //when data is retrieved from backgroundDatabase, add the data to the recyclerView
            //SCREENSHOT MADE: consider switchMap
            sharedViewModel.dinosaurData.observe(viewLifecycleOwner) {
                allWidgets.observe(viewLifecycleOwner) { widgetData ->
                    binding.profileBadgesRecycler.adapter = ProfileAdapter(
                        it,
                        widgetData,
                        requireContext())
                }
            }
            //SCREENSHOT MADE: consider switchMap

            //when data is retrieved from backgroundDatabase, add the data to the profile image
            profileImage.observe(viewLifecycleOwner) {
                binding.profileImage.setImageResource(it)
            }
        }

        //when fab is selected navigate to the profile edit screen
        binding.editProfileImageFab.setOnClickListener {
            //change transition from MaterialFade
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)

            //pass list of dinosaurs (plus default profile image) to ProfileEditFragment
            //to prevent another creation and call from backgroundDatabase
            val bundle = Bundle()
            val allDinos = ArrayList(sharedViewModel.dinosaurData.value!!)
            allDinos.add(0, DinosaurEncyclopedia(
                -1,
                R.drawable.profile_icon,
                0,
                0,
                0,
                true)
            )
            bundle.putParcelableArrayList("activatedDinos", allDinos as ArrayList<out Parcelable>)
            findNavController().navigate(R.id.profile_edit_fragment, bundle)
        }

        return binding.root
    }
}

@BindingAdapter("nextLevel")
fun TextView.nextLevel(value: LiveData<Int>) {
    val level = value.value
    text = if(level == 3) {
        "3"
    } else {
        level?.plus(1).toString()
    }
}

