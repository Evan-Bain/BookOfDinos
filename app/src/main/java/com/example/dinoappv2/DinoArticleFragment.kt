package com.example.dinoappv2

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DictionaryStrings
import com.example.dinoappv2.dataClasses.DinosaurArticleStrings
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databinding.FragmentDinoArticleBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import com.example.dinoappv2.viewModels.DinoArticleViewModelFactory
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import java.util.*


class DinoArticleFragment : Fragment() {

    private lateinit var binding: FragmentDinoArticleBinding
    private lateinit var viewModel: DinoArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        //creating viewModel
        val viewModelFactory = DinoArticleViewModelFactory(
            DinosaurEncyclopediaDatabase.getInstance(requireContext()).dinosaurEncyclopediaDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DinoArticleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dino_article,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.fragment = this

        val dinoSelected = arguments?.get("dinoSelected") as DinosaurEncyclopedia

        val dinoPosition = dinoSelected.position
        val habitatArticle = SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[1]?.get(0))
        val evolutionArticle = SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[2]?.get(0))
        val fossilArticle = SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[3]?.get(0))

        lifecycleScope.launch {
            DictionaryStrings.getDictionaryStrings()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    for(element in it) {
                        val word = element.word.lowercase(Locale.getDefault())
                        val clickableSpan: ClickableSpan = object : ClickableSpan() {
                            override fun onClick(widget: View) {
                                val bundle = bundleOf("selectedWord" to word)
                                findNavController().navigate(R.id.dictionary_bottom_nav, bundle)
                            }

                            override fun updateDrawState(ds: TextPaint) {
                                super.updateDrawState(ds)
                                ds.isUnderlineText = false
                                ds.isFakeBoldText = true
                            }
                        }
                        if(habitatArticle.contains(word)) {
                            val first = habitatArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while(whileLoop) {
                                val newWord = habitatArticle[first + iterator].toString()
                                if(newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?") {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            habitatArticle.setSpan(clickableSpan, first, first+iterator, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                        if(evolutionArticle.contains(word)) {
                            val first = evolutionArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while(whileLoop) {
                                val newWord = evolutionArticle[first + iterator].toString()
                                if(newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?") {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            evolutionArticle.setSpan(clickableSpan, first, first+iterator, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                        if(fossilArticle.contains(word)) {
                            val first = fossilArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while(whileLoop) {
                                val newWord = fossilArticle[first + iterator].toString()
                                if(newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?") {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            fossilArticle.setSpan(clickableSpan, first, first+iterator, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    }
                    with(binding) {
                        habitatText.text = habitatArticle
                        crazyEvolutionText.text = evolutionArticle
                        fossilHistoryText.text = fossilArticle
                    }
                }
        }

        with(binding) {
            habitatConstraintLayout.setOnClickListener {
                viewModel?.habitatDropDownClicked()
            }
            evolutionConstraintLayout.setOnClickListener {
                viewModel?.evolutionDropDownClicked()
            }
            fossilConstraintLayout.setOnClickListener {
                viewModel?.fossilDropDownClicked()
            }
        }

        viewModel.habitatDroppedDown.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    habitatMotionLayout.transitionToEnd()
                    habitatDropButton.setImageResource(R.drawable.drop_down_arrow_up)
                }
            } else {
                with(binding) {
                    habitatMotionLayout.transitionToStart()
                    habitatDropButton.setImageResource(R.drawable.drop_down_arrow_down)
                }
            }
        }

        viewModel.evolutionDroppedDown.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    evolutionMotionLayout.transitionToEnd()
                    crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_up)
                }
            } else {
                with(binding) {
                    evolutionMotionLayout.transitionToStart()
                    crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_down)
                }
            }
        }

        viewModel.fossilDroppedDown.observe(viewLifecycleOwner) {

            if (it) {
                with(binding) {
                    fossilMotionLayout.transitionToEnd()
                    fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_up)
                }
            } else {
                with(binding) {
                    fossilMotionLayout.transitionToStart()
                    fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_down)
                }
            }
        }

        with(binding) {
            val dinosaurStrings = DinosaurArticleStrings(dinoPosition).getDinoStrings()
            heightFactText.text = dinosaurStrings[0]!![0]
            heightFactText.movementMethod = LinkMovementMethod.getInstance()
            weightFactText.text = dinosaurStrings[0]!![1]
            livedFactText.text = dinosaurStrings[0]!![2]
            speedFactText.text = dinosaurStrings[0]!![3]
            nameFactText.text = dinosaurStrings[0]!![4]
            habitatText.movementMethod = LinkMovementMethod.getInstance()
            crazyEvolutionText.movementMethod = LinkMovementMethod.getInstance()
            fossilHistoryText.movementMethod = LinkMovementMethod.getInstance()
        }
        //set quiz button enabled or not
        viewModel.quizButtonEnabled.observe(viewLifecycleOwner) {
            binding.quizButton.isEnabled = it
        }

        //set whether or not quiz nav host is visible
        /*viewModel.quizVisible.observe(viewLifecycleOwner) {
            binding.quizNavHost.visibility = if (it) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }*/

        /*viewModel.articleBodyAlpha.observe(this, {
            binding.quickFacts.alpha = if(it) {
                1F
            } else {
                .01F
            }
        })*/

        return binding.root
    }

    //used in quiz button onClick data binding to display quiz and reset variables
    //from last instance
    fun quizOnClick() {
        with(viewModel) {
            setNextButtonClicked(1)
            setAnswersCorrect(0)
            setQuizVisible(true)
            setQuizButton(false)
            setArticleBodyAlpha(false)
        }
    }

    private fun MotionLayout.setClickListener() {

        with(binding) {
            habitatConstraintLayout.setOnClickListener {
                viewModel?.habitatDropDownClicked()
            }
            evolutionConstraintLayout.setOnClickListener {
                viewModel?.evolutionDropDownClicked()
            }
            fossilConstraintLayout.setOnClickListener {
                viewModel?.fossilDropDownClicked()
            }
        }

        setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    with(binding) {
                        habitatConstraintLayout.setOnClickListener(null)
                        evolutionConstraintLayout.setOnClickListener(null)
                        fossilConstraintLayout.setOnClickListener(null)
                    }
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    with(binding) {
                        habitatConstraintLayout.setOnClickListener(null)
                        evolutionConstraintLayout.setOnClickListener(null)
                        fossilConstraintLayout.setOnClickListener(null)
                    }                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    with(binding) {
                        habitatConstraintLayout.setOnClickListener {
                            viewModel?.habitatDropDownClicked()
                        }
                        evolutionConstraintLayout.setOnClickListener {
                            viewModel?.evolutionDropDownClicked()
                        }
                        fossilConstraintLayout.setOnClickListener {
                            viewModel?.fossilDropDownClicked()
                        }
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                    with(binding) {
                        habitatConstraintLayout.setOnClickListener(null)
                        evolutionConstraintLayout.setOnClickListener(null)
                        fossilConstraintLayout.setOnClickListener(null)
                    }
                }

            }
        )
    }

    //viewModel visible to quizFragment
    companion object {
        lateinit var viewModel: DinoArticleViewModel
    }
}