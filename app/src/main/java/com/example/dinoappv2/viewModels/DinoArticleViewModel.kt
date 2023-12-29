package com.example.dinoappv2.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.dinoappv2.R

class DinoArticleViewModel : ViewModel() {

    private var _initialized = false
    val initialized: Boolean
        get() = _initialized

    fun setInitialized() {
        _initialized = true
    }

    private var _quizPassed = false
    val quizPassed: Boolean
        get() = _quizPassed

    fun setQuizPassed() {
        _quizPassed = true
    }

    //holds value for whether or not the habitat info is displayed
    private val _habitatDroppedDown = MutableLiveData<Boolean>()
    val habitatDroppedDown: LiveData<Boolean>
        get() = _habitatDroppedDown

    fun habitatDropDownClicked() {
        _habitatDroppedDown.value = !(_habitatDroppedDown.value ?: false)
    }

    //holds value for whether or not the evolution info is displayed
    private val _evolutionDroppedDown = MutableLiveData<Boolean>()
    val evolutionDroppedDown: LiveData<Boolean>
        get() = _evolutionDroppedDown

    fun evolutionDropDownClicked() {
        _evolutionDroppedDown.value = !(_evolutionDroppedDown.value ?: false)
    }

    //holds value for whether or not the fossil info is displayed
    private val _fossilDroppedDown = MutableLiveData<Boolean>()
    val fossilDroppedDown: LiveData<Boolean>
        get() = _fossilDroppedDown

    fun fossilDropDownClicked() {
        _fossilDroppedDown.value = !(_fossilDroppedDown.value ?: false)
    }

    //reading section that is being listened to
    //-1 = none
    //null = audio has not been initialized
    private var _currentAudio: Int? = null
    val currentAudio: Int?
        get() = _currentAudio

    //calls fragment to reset subtitle position to 0
    private val _resetSubtitles = MutableLiveData<Boolean>()
    val resetSubtitles: LiveData<Boolean>
        get() = _resetSubtitles

    fun setCurrentAudio(value: Int?) {
        _resetSubtitles.value = !(_resetSubtitles.value ?: false)
        _currentAudio = value
    }

    //used in data binding to determine what radio button was clicked
    private val _radioButtonClicked = MutableLiveData<Int>()
    val radioButtonClicked: LiveData<Int>
        get() = _radioButtonClicked

    fun setRadioButtonClicked(position: Int) {

        _radioButtonClicked.value = when(position) {
            R.id.quiz_radio_button_0 -> 0
            R.id.quiz_radio_button_1 -> 1
            R.id.quiz_radio_button_2 -> 2
            R.id.quiz_radio_button_3 -> 3
            else -> -1
        }
    }

    val nextButtonEnabled: LiveData<Boolean> = Transformations.map(radioButtonClicked) {
        return@map it != -1
    }

    //determine what quiz question to display
    private val _nextButtonClicked = MutableLiveData<Int>()
    val nextButtonClicked: LiveData<Int>
        get() = _nextButtonClicked


    /** indicate what page of quiz is displayed  **/
    fun nextButtonClicked(value: Int) {
        _nextButtonClicked.value = when(value) {
            -1 -> (nextButtonClicked.value ?: 0) + 1
            0 -> null
            else -> {
                _answersCorrect = 5
                value
            }
        }
    }

    //question and answers for quiz
    private val quizStrings = MutableLiveData<List<String>>()

    //all answers and question updated with dataBinding
    val quizQuestion = Transformations.map(quizStrings) {
        return@map it[0]
    }

    val firstAnswer = Transformations.map(quizStrings) {
        return@map it[1]
    }

    val secondAnswer = Transformations.map(quizStrings) {
        return@map it[2]
    }

    val thirdAnswer = Transformations.map(quizStrings) {
        return@map it[3]
    }

    val fourthAnswer = Transformations.map(quizStrings) {
        return@map it[4]
    }

    /** update question and answers through dataBinding **/
    fun setQuizStringAnswers(list: List<String>) {
        quizStrings.value = list
    }

    //holds the value of how many questions are answered correctly
    private var _answersCorrect = 0
    val answersCorrect: Int
        get() = _answersCorrect

    fun updateAnswerCorrect() {
        _answersCorrect++
    }

    fun resetAnswersCorrect() {
        _answersCorrect = 0
    }

    val resultMessageText = Transformations.map(nextButtonClicked) {
        setResultText("Congratulations", "Unfortunately")
    }

    val resultText = Transformations.map(nextButtonClicked) {
        setResultText("You Passed", "You Failed")
    }

    val percentText = Transformations.map(nextButtonClicked) {
        "${_answersCorrect*20}%"
    }

    private fun setResultText(passText: String, failText: String): String {
        return if(_answersCorrect > 3) {
            passText
        } else {
            failText
        }
    }
}