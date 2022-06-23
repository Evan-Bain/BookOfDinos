package com.example.dinoappv2.viewModels

import androidx.lifecycle.*
import com.example.dinoappv2.R
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DinoArticleViewModel(
    private val database: DinosaurEncyclopediaDao,
    private val answers: List<Int>): ViewModel() {

    //holds value for whether or not the habitat info is displayed
    private val _habitatDroppedDown = MutableLiveData(false)
    val habitatDroppedDown: LiveData<Boolean>
        get() = _habitatDroppedDown

    fun habitatDropDownClicked() {
        _habitatDroppedDown.value = !_habitatDroppedDown.value!!
    }

    //holds value for whether or not the evolution info is displayed
    private val _evolutionDroppedDown = MutableLiveData(false)
    val evolutionDroppedDown: LiveData<Boolean>
        get() = _evolutionDroppedDown

    fun evolutionDropDownClicked() {
        _evolutionDroppedDown.value = !_evolutionDroppedDown.value!!
    }

    //holds value for whether or not the fossil info is displayed
    private val _fossilDroppedDown = MutableLiveData(false)
    val fossilDroppedDown: LiveData<Boolean>
        get() = _fossilDroppedDown

    fun fossilDropDownClicked() {
        _fossilDroppedDown.value = !_fossilDroppedDown.value!!
    }

    //used in data binding to determine what radio button was clicked
    private val radioButtonClicked = MutableLiveData<Int?>(null)

    fun setRadioButtonClicked(position: Int) {
        radioButtonClicked.value = when(position) {
            R.id.quiz_radio_button_0 -> 0
            R.id.quiz_radio_button_1 -> 1
            R.id.quiz_radio_button_2 -> 2
            R.id.quiz_radio_button_3 -> 3
            else -> null
        }
    }

    val nextButtonEnabled: LiveData<Boolean> = Transformations.map(radioButtonClicked) {
        return@map it != null
    }

    //determine what quiz question to display
    private val _nextButtonClicked = MutableLiveData(0)
    val nextButtonClicked: LiveData<Int>
        get() = _nextButtonClicked


    /** sets logic for when the user has selected an answer **/
    fun nextButtonClicked() {

        //removes what question was clicked to not appear on next question
        radioButtonClicked.value = null

        _nextButtonClicked.value?.let {

            //if answer was correct
            if(answers[it] == radioButtonClicked.value) {
                answersCorrect++
            }

            //indicate user selected an answer
            _nextButtonClicked.value = it + 1
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
    private var answersCorrect: Int = 0

    //calls database function to update activated value
    fun updateActivated(activated: Boolean, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            database.updateActivated(
                if(activated) 1 else 0,
                position)
        }
    }
}

class DinoArticleViewModelFactory(
    private val dataSource: DinosaurEncyclopediaDao,
    private val answers: List<Int>
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DinoArticleViewModel::class.java)) {
            return DinoArticleViewModel(dataSource, answers) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}