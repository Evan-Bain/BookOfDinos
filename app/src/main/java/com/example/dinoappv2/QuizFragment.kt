package com.example.dinoappv2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.bottomNav.BottomNavActivity
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DinosaurQuizStrings
import com.example.dinoappv2.databinding.FragmentQuizBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel

class QuizFragment : Fragment() {

    lateinit var viewModel: DinoArticleViewModel

    lateinit var binding: FragmentQuizBinding

    private lateinit var quizStrings: HashMap<Int, List<String>>

    private lateinit var answerPosition: List<Int>

    private val currentDino = DinosaurQuizStrings(
        CompanionObject.dinoArticleSelected!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quiz,
            container,
            false
        )
        viewModel = DinoArticleActivity.viewModel

        binding.viewModel = viewModel
        binding.fragment = this

        //string values used for layout
        quizStrings = currentDino.getDinoStrings()
        //what radioButton positions are the correct answer
        answerPosition = currentDino.getCorrectAnswers()

        binding.quizFinishButton.isEnabled = false
        binding.quizTitle.text = currentDino.getDinoStrings()[-1]!![0]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //if a radio button is clicked then the next question
        //button is enabled
        viewModel.radioButtonClicked.observe(viewLifecycleOwner, {
            binding.quizFinishButton.isEnabled = true
        })

        //when nextButton's value is changed (new quiz question)
        //the layout changes appropriately
        viewModel.nextButtonClicked.observe(viewLifecycleOwner, {
            if(it == 5) {
                //sets layout to display score and whether or not
                //quiz was passed
                val score = viewModel.answersCorrect.value!! * 25
                binding.quizScore.text = resources.getString(R.string.quiz_percent, score)
                if(score >= 70) {
                    binding.quizComment.text = resources.getString(R.string.you_passed)
                    viewModel.updateActivated(true,
                        CompanionObject.dinoArticleSelected!!)
                } else {
                    binding.quizComment.text = resources.getString(R.string.you_failed)
                }
                binding.quizQuestionLayout.visibility = GONE
                binding.quizResultLayout.visibility = View.VISIBLE
            } else {
                binding.quizQuestionLayout.visibility = View.VISIBLE
                binding.quizResultLayout.visibility = GONE
                binding.quizQuestion.text = quizStrings[0]?.get(it - 1) ?: "ERROR"
                binding.quizRadioButton0.text = quizStrings[it]?.get(0) ?: "ERROR"
                binding.quizRadioButton1.text = quizStrings[it]?.get(1) ?: "ERROR"
                binding.quizRadioButton2.text = quizStrings[it]?.get(2) ?: "ERROR"
                binding.quizRadioButton3.text = quizStrings[it]?.get(3) ?: "ERROR"
                binding.quizFinishButton.isEnabled = false
            }
        })
    }

    //function used in dataBinding that sets appropriate variables
    //and navigates to opposite fragment
    fun nextButtonClicked() {
        val checkedButton = when(binding.quizRadioGroup.checkedRadioButtonId) {
            R.id.quiz_radio_button_0 -> 0
            R.id.quiz_radio_button_1 -> 1
            R.id.quiz_radio_button_2 -> 2
            else -> 3
        }
        if(answerPosition[viewModel.nextButtonClicked.value!! - 1]
            == checkedButton) {
            viewModel.setAnswersCorrect(viewModel.answersCorrect.value!! + 1)
        }
        viewModel.setNextButtonClicked(
            viewModel.nextButtonClicked.value!! + 1)
        findNavController().navigate(
            QuizFragmentDirections.actionQuizFragmentToQuizNextFragment())
    }

    //function used in dataBinding of quiz score layout to
    //make the quiz disappear and navigate out of article
    fun finishButtonClicked() {
        val intent = Intent(activity, BottomNavActivity::class.java)
        startActivity(intent)
        viewModel.setQuizVisible(false)
        viewModel.setQuizButton(true)
    }
}