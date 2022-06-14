package com.example.dinoappv2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.databinding.SelectBackgroundFragmentBinding
import com.example.dinoappv2.viewModels.MainViewModel
import com.example.dinoappv2.viewModels.SelectBackgroundViewModel
import com.google.android.material.transition.MaterialSharedAxis

class SelectBackgroundFragment : Fragment() {

    private lateinit var viewModel: SelectBackgroundViewModel

    //viewModel shared from MainActivity
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: SelectBackgroundFragmentBinding

    //background preview image previously clicked
    private var lastClicked: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SelectBackgroundViewModel::class.java]

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
            R.layout.select_background_fragment,
            container,
            false
        )

        //disable nested scrolling to not activate collapsable toolbar
        ViewCompat.setNestedScrollingEnabled(binding.selectBackgroundScrollView, false)

        viewModel.backgroundSelected.observe(viewLifecycleOwner) {
            selectBackgroundImage(it)
        }

        with(binding) {
            defaultBackground.setOnClickListener {
                viewModel.setBackgroundSelected(-1)
            }

            jungleBackground.setOnClickListener {
                viewModel.setBackgroundSelected(0)
            }

            oceanBackground.setOnClickListener {
                viewModel.setBackgroundSelected(1)
            }

            skyBackground.setOnClickListener {
                viewModel.setBackgroundSelected(2)
            }

            //changes background to selected background and navigates to ProfileFragment
            selectBackground.setOnClickListener {
                with(sharedViewModel) {
                    //enables app to recreate, allowing a new style to be selected
                    setBackground(true)
                    saveBackground(viewModel.backgroundSelected.value ?: -1)
                    changeBackground(lastClicked ?: -1)
                }
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    /**logic for determining what background is currently selected while also displaying
     * check mark over background that is selected**/
    private fun selectBackgroundImage(current: Int) {
        if(lastClicked == null) {
            //if a background has not been selected yet or fragment refreshed
            lastClicked = current

            //keeps logic for clicked again consistent after refreshing
            viewModel.setClickedAgain(!viewModel.clickedAgain)
        }

        if(viewModel.clickedAgain && lastClicked != current) {
            //if clicked again is defined as true but logically is not true
            //make false
            viewModel.setClickedAgain(false)
        }

        with(binding) {

            if(lastClicked == current) {
                //if the same background was clicked

                //determines whether to disable button if the background is selected or not
                selectBackground.animateFade(viewModel.clickedAgain)
                selectBackground.isClickable = viewModel.clickedAgain

                if(viewModel.clickedAgain) {
                    //if click is an odd number
                    when (current) {
                        //display check
                        -1 -> defaultBackground.animateCheckIn(defaultBackgroundCheck)
                        0 -> jungleBackground.animateCheckIn(jungleBackgroundCheck)
                        1 -> oceanBackground.animateCheckIn(oceanBackgroundCheck)
                        2 -> skyBackground.animateCheckIn(skyBackgroundCheck)
                    }
                } else {
                    //if click is an even number
                    when(lastClicked) {
                        //hide check
                        -1 -> defaultBackground.animateCheckOut(defaultBackgroundCheck)
                        0 -> jungleBackground.animateCheckOut(jungleBackgroundCheck)
                        1 -> oceanBackground.animateCheckOut(oceanBackgroundCheck)
                        2 -> skyBackground.animateCheckOut(skyBackgroundCheck)
                    }
                }

                //change clicked again from the previous value (opposite)
                viewModel.setClickedAgain(!viewModel.clickedAgain)
            } else {
                if(!selectBackground.isClickable) {
                    //if button cannot be clicked, make button clickable
                    selectBackground.isClickable = true

                    //fade button if button was not visible
                    selectBackground.animateFade(true)
                }

                when(current) {
                    //make check appear on current background clicked
                    -1 -> defaultBackground.animateCheckIn(defaultBackgroundCheck)
                    0 -> jungleBackground.animateCheckIn(jungleBackgroundCheck)
                    1 -> oceanBackground.animateCheckIn(oceanBackgroundCheck)
                    2 -> skyBackground.animateCheckIn(skyBackgroundCheck)
                }

                when(lastClicked) {
                    //make check disappear on previous background clicked
                    -1 -> defaultBackground.animateCheckOut(defaultBackgroundCheck)
                    0 -> jungleBackground.animateCheckOut(jungleBackgroundCheck)
                    1 -> oceanBackground.animateCheckOut(oceanBackgroundCheck)
                    2 -> skyBackground.animateCheckOut(skyBackgroundCheck)
                }
            }
        }

        //set lastClicked to current to be reused on next click
        lastClicked = current
    }

    /** animates check selection visible **/
    private fun CardView.animateCheckIn(checkView: ImageView) {
        //NOTE: Radius value in float is 3x dp (ex: 36f == 12dp)
        val cardCornersAnim =
            ObjectAnimator.ofFloat(this, "radius", 36f).apply {
                duration = 500
            }
        val fadeCheckAnim =
            ObjectAnimator.ofFloat(checkView, "alpha", 1f).apply {
                duration = 500
            }

        AnimatorSet().apply {
            playTogether(listOf(cardCornersAnim, fadeCheckAnim))
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    /** animates check selection invisible **/
    private fun CardView.animateCheckOut(checkView: ImageView) {
        //NOTE: Radius value in float is 3x dp (ex: 24f == 8dp)
        val cardCornersAnim =
            ObjectAnimator.ofFloat(this, "radius", 24f).apply {
                duration = 500
            }
        val fadeCheckAnim =
            ObjectAnimator.ofFloat(checkView, "alpha", 0f).apply {
                duration = 500
            }
        AnimatorSet().apply {
            playTogether(listOf(cardCornersAnim, fadeCheckAnim))
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    /** fades in/out button **/
    private fun Button.animateFade(visible: Boolean) {
        val alphaValue = if(visible) {
            1f
        } else {
            0f
        }

        ObjectAnimator.ofFloat(this, "alpha", alphaValue).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

}