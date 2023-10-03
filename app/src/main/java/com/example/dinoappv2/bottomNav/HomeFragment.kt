package com.example.dinoappv2.bottomNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.databinding.FragmentHomeBinding
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_home,
            container,
            false)

        //re-enable normal transitions after navigating away from ProfileEditFragment
        val prevDestination = findNavController().previousBackStackEntry?.destination?.id
        if(prevDestination == R.id.dinoErasFragment ||
            prevDestination == R.id.dinoExtinctionFragment) {
            enterTransition = MaterialFadeThrough()
            exitTransition = MaterialFadeThrough()
            reenterTransition = MaterialFadeThrough()
        }

        binding.dinosaurErasCard.setOnClickListener {

            //change transition from MaterialFade
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)

            findNavController().navigate(R.id.dinoErasFragment)
        }

        binding.dinosaurExtinctionCard.setOnClickListener {

            //change transition from MaterialFade
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)

            findNavController().navigate(R.id.dinoExtinctionFragment)
        }

        return binding.root
    }
}