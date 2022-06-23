package com.example.dinoappv2.miscFragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.bottomNav.MainActivity
import com.example.dinoappv2.dataClasses.*
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databinding.FragmentDinoArticleBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import com.example.dinoappv2.viewModels.DinoArticleViewModelFactory
import com.example.dinoappv2.viewModels.MainViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import java.util.*


class DinoArticleFragment : Fragment() {

    private lateinit var binding: FragmentDinoArticleBinding
    private lateinit var viewModel: DinoArticleViewModel

    //allows back button & up button to be overridden during quiz
    private val sharedViewModel: MainViewModel by activityViewModels()

    //disables quiz from auto animating on entering (CONSIDER USING SingleLiveEvent)
    private var init = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    //NOTE: ADD KEYFRAME FOR RESIZABLE VIEWS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dino_article,
            container,
            false
        )
        val dinoSelected = arguments?.get("dinoSelected") as DinosaurEncyclopedia

        //creating viewModel
        val viewModelFactory = DinoArticleViewModelFactory(
            DinosaurEncyclopediaDatabase.getInstance(requireContext()).dinosaurEncyclopediaDao,
            getCorrectAnswers(dinoSelected.position)
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[DinoArticleViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel

        val dinoPosition = dinoSelected.position
        val quizStrings = getDinoStrings(dinoPosition)

        //NULL FOR TESTING ONLY
        binding.quizTitle.text = quizStrings?.get(-1)?.get(0) ?: "NULL Quiz"
        //NULL FOR TESTING ONLY
        //set spannable strings for each section of info to allow difficult words to be pressed
        val habitatArticle =
            SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[1]?.get(0))
        val evolutionArticle =
            SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[2]?.get(0))
        val fossilArticle =
            SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[3]?.get(0))

        lifecycleScope.launch {
            DictionaryStrings.getDictionaryStrings()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    for (element in it) {
                        val word = element.word.lowercase(Locale.getDefault())
                        val clickableSpan: ClickableSpan = object : ClickableSpan() {
                            override fun onClick(widget: View) {
                                Log.i("DinoArticleFragment", "clicked")
                                //navigate to the dictionary and display definition of the word
                                exitTransition =
                                    MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
                                        duration = 750
                                    }
                                val bundle = bundleOf("selectedWord" to word)
                                sharedViewModel.setDictionaryWord(true)
                                findNavController().navigate(
                                    R.id.dictionary_bottom_nav,
                                    bundle,

                                    //set home screen to top of backStack to keep
                                    //consistent user navigation (and not break bottomNav)
                                    NavOptions.Builder().apply {
                                        setPopUpTo(R.id.home_bottom_nav, false)
                                    }.build()
                                )
                            }

                            override fun updateDrawState(ds: TextPaint) {
                                //set style of clickable words to bolded with the secondary color
                                super.updateDrawState(ds)
                                ds.isUnderlineText = false
                                ds.isFakeBoldText = true
                            }
                        }

                        //find words that are in the dictionary in the info section
                        if (habitatArticle.contains(word)) {
                            val first = habitatArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while (whileLoop) {
                                val newWord = habitatArticle[first + iterator].toString()
                                if (newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?"
                                ) {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            habitatArticle.setSpan(
                                clickableSpan,
                                first,
                                first + iterator,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }

                        //find words that are in the dictionary in the info section
                        if (evolutionArticle.contains(word)) {
                            val first = evolutionArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while (whileLoop) {
                                val newWord = evolutionArticle[first + iterator].toString()
                                if (newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?"
                                ) {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            evolutionArticle.setSpan(
                                clickableSpan,
                                first,
                                first + iterator,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }

                        //find words that are in the dictionary in the info section
                        if (fossilArticle.contains(word)) {
                            val first = fossilArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while (whileLoop) {
                                val newWord = fossilArticle[first + iterator].toString()
                                if (newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?"
                                ) {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            fossilArticle.setSpan(
                                clickableSpan,
                                first,
                                first + iterator,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
                    with(binding) {
                        habitatText.text = habitatArticle
                        crazyEvolutionText.text = evolutionArticle
                        fossilHistoryText.text = fossilArticle
                    }
                }
        }

        //Disables transitions when in the active state to avoid glitch
        with(binding) {
            habitatConstraintLayout.setTransitionListeners(habitatMotionLayout, 0)
            evolutionConstraintLayout.setTransitionListeners(evolutionMotionLayout, 1)
            fossilConstraintLayout.setTransitionListeners(fossilMotionLayout, 2)
        }

        sharedViewModel.quizVisible.observe(viewLifecycleOwner) {
            displayQuiz(it)

            //enables/disables up button if quiz is open or not
            (activity as MainActivity).binding.activityToolbar.navigationIcon = if (it) {
                null
            } else {
                AppCompatResources.getDrawable(requireContext(), R.drawable.back_button)
            }

        }

        viewModel.nextButtonEnabled.observe(viewLifecycleOwner) {
            with(binding.quizMotionLayout) {
                if (it) {
                    transitionToEnd()
                } else {

                    if (viewModel.nextButtonClicked.value != 4) {
                        transitionToStart()
                    } else {
                        //if there are no more answers display result page
                        setTransition(R.id.result_transition)
                        transitionToEnd {

                            //animate layout resize
                            setTransition(R.id.result_resize_transition)
                            transitionToEnd()
                        }
                    }
                }
            }
        }

        viewModel.nextButtonClicked.observe(viewLifecycleOwner) {
            //make sure quiz is visible or main thread is stalled continuously
            binding.quizRadioGroup.clearCheck()

            //only set the text for quiz for how many questions there are
            if (it < 4) {
                //NULL FOR TESTING ONLY
                //set text in quiz with dataBinding
                viewModel.setQuizStringAnswers(
                    listOf(
                        quizStrings?.get(0)?.get(it)
                            ?: "If the question is NULL what is the answer?",
                        quizStrings?.get(it + 1)?.get(0)
                            ?: "answer is 1023",
                        quizStrings?.get(it + 1)?.get(1)
                            ?: "answer is 1023",
                        quizStrings?.get(it + 1)?.get(2)
                            ?: "answer is 1023",
                        quizStrings?.get(it + 1)?.get(3)
                            ?: "answer is 1023"
                    )
                )
                //NULL FOR TESTING ONLY
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

        //sets text in article
        with(binding) {
            val dinosaurStrings = DinosaurArticleStrings(dinoPosition).getDinoStrings()
            heightFactText.text = dinosaurStrings[0]!![0]
            weightFactText.text = dinosaurStrings[0]!![1]
            livedFactText.text = dinosaurStrings[0]!![2]
            speedFactText.text = dinosaurStrings[0]!![3]
            nameFactText.text = dinosaurStrings[0]!![4]
        }

        return binding.root
    }

    private fun displayQuiz(visible: Boolean) {
        val displayInfo = ObjectAnimator.ofFloat(
            binding.constraintArticleLayout, "alpha", if (visible) 0f else 1f
        )

        val displayQuizX = ObjectAnimator.ofFloat(
            binding.quizCardLayout, "scaleX", if (visible) 1f else 0f
        )

        val displayQuizY = ObjectAnimator.ofFloat(
            binding.quizCardLayout, "scaleY", if (visible) 1f else 0f
        )

        //NOTE: PLAYS WHEN APP FIRST ENTERS
        AnimatorSet().apply {
            playTogether(listOf(displayInfo, displayQuizX, displayQuizY))
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()

            if (visible) {
                doOnStart {
                    ViewCompat.setNestedScrollingEnabled(binding.scrollArticleLayout, false)
                    binding.quizCardLayout.visibility = View.VISIBLE
                    binding.scrollArticleLayout.isNestedScrollingEnabled = false
                }
                doOnEnd {
                    binding.backgroundMask.visibility = View.VISIBLE
                    binding.scrollArticleLayout.scrollTo(0, 0)
                }
            } else {
                doOnStart {
                    ViewCompat.setNestedScrollingEnabled(binding.scrollArticleLayout, true)
                    binding.scrollArticleLayout.isNestedScrollingEnabled = true
                    binding.backgroundMask.visibility = View.GONE
                    if (!init) {
                        binding.quizCardLayout.visibility = View.GONE
                    }
                }
                doOnEnd {
                    binding.quizCardLayout.visibility = View.GONE
                    init = true
                }
            }

            start()
        }
        //NOTE: PLAYS WHEN APP FIRST ENTERS
    }

    /** Disables transitions when in the active state **/
    private fun FrameLayout.setTransitionListeners(motionLayout: MotionLayout, layout: Int) {
        motionLayout.setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    //disables spannable text clicks when animating to prevent scroll glitch
                    when (layout) {
                        0 -> binding.habitatText.movementMethod = null
                        1 -> binding.crazyEvolutionText.movementMethod = null
                        2 -> binding.fossilHistoryText.movementMethod = null
                    }
                    isClickable = false
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {}

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    when (layout) {
                        0 -> binding.habitatText.movementMethod =
                            LinkMovementMethod.getInstance()
                        1 -> binding.crazyEvolutionText.movementMethod =
                            LinkMovementMethod.getInstance()
                        2 -> binding.fossilHistoryText.movementMethod =
                            LinkMovementMethod.getInstance()
                    }
                    isClickable = true
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {}
            }
        )
    }
}