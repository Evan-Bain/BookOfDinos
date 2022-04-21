package com.example.dinoappv2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.dataClasses.DinosaurQuizStrings
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databinding.FragmentQuizNextBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import com.example.dinoappv2.viewModels.DinoArticleViewModelFactory

class QuizNextFragment : Fragment() {

    private lateinit var viewModel: DinoArticleViewModel

    private lateinit var binding: FragmentQuizNextBinding

    private lateinit var quizStrings: HashMap<Int, List<String>>

    private lateinit var answerPosition: List<Int>

    private val currentDino = DinosaurQuizStrings(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_quiz_next,
            container,
            false
        )

        //creating viewModel
        val viewModelFactory = DinoArticleViewModelFactory(
            DinosaurEncyclopediaDatabase.getInstance(requireContext()).dinosaurEncyclopediaDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            DinoArticleViewModel::class.java)

        binding.viewModel = viewModel
        binding.fragment = this

        //string values used for layout
        quizStrings = currentDino.getDinoStrings()
        //what radioButton positions are the correct answer
        answerPosition = currentDino.getCorrectAnswers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //if a radio button is clicked then the next question
        //button is enabled
        viewModel.radioButtonClicked.observe(viewLifecycleOwner) {
            binding.quizFinishButtonNext.isEnabled = true
        }

        //when nextButton's value is changed (new quiz question)
        //the layout changes appropriately
        viewModel.nextButtonClicked.observe(viewLifecycleOwner) {
            if (it <= 4) {
                binding.quizQuestionNext.text = quizStrings[0]?.get(it - 1) ?: "ERROR"
                binding.quizRadioButton0Next.text = quizStrings[it]?.get(0) ?: "ERROR"
                binding.quizRadioButton1Next.text = quizStrings[it]?.get(1) ?: "ERROR"
                binding.quizRadioButton2Next.text = quizStrings[it]?.get(2) ?: "ERROR"
                binding.quizRadioButton3Next.text = quizStrings[it]?.get(3) ?: "ERROR"
                binding.quizFinishButtonNext.isEnabled = false
            }
        }
    }

    //function used in dataBinding that sets appropriate variables
    //and navigates to opposite fragment
    fun nextButtonClicked() {
        if(answerPosition[viewModel.nextButtonClicked.value!! - 1]
            == viewModel.radioButtonClicked.value) {
            viewModel.setAnswersCorrect(viewModel.answersCorrect.value!! + 1)
        }
        viewModel.setNextButtonClicked(
            viewModel.nextButtonClicked.value!! + 1)
        findNavController().navigate(
            QuizNextFragmentDirections.actionQuizNextFragmentToQuizFragment())
    }

}