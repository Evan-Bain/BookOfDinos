package com.example.dinoappv2.viewModels

import androidx.lifecycle.*
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import kotlinx.coroutines.launch

class DinoArticleViewModel(private val database: DinosaurEncyclopediaDao): ViewModel() {

    //holds value for whether or not the habitat info is displayed
    private val _habitatDroppedDown = MutableLiveData<Boolean>()
    val habitatDroppedDown: LiveData<Boolean>
        get() = _habitatDroppedDown

    fun habitatDropDownClicked() {
        _habitatDroppedDown.value = !_habitatDroppedDown.value!!
    }

    //holds value for whether or not the evolution info is displayed
    private val _evolutionDroppedDown = MutableLiveData<Boolean>()
    val evolutionDroppedDown: LiveData<Boolean>
        get() = _evolutionDroppedDown

    fun evolutionDropDownClicked() {
        _evolutionDroppedDown.value = !_evolutionDroppedDown.value!!
    }

    //holds value for whether or not the fossil info is displayed
    private val _fossilDroppedDown = MutableLiveData<Boolean>()
    val fossilDroppedDown: LiveData<Boolean>
        get() = _fossilDroppedDown

    fun fossilDropDownClicked() {
        _fossilDroppedDown.value = !_fossilDroppedDown.value!!
    }

    //used in data binding to determine what radio button was
    //clicked
    private val _radioButtonClicked = MutableLiveData<Int?>()
    val radioButtonClicked: LiveData<Int?>
        get() = _radioButtonClicked

    fun setRadioButtonClicked(position: Int) {
        _radioButtonClicked.value = position
    }

    //set whether the button to go to the next question is
    //is enabled or not
    private val _quizButtonEnabled = MutableLiveData<Boolean>()
    val quizButtonEnabled: LiveData<Boolean>
        get() = _quizButtonEnabled

    fun setQuizButton(enabled: Boolean) {
        _quizButtonEnabled.value = enabled
    }

    //determine what quiz question to display and the current question
    //that is displayed
    private val _nextButtonClicked = MutableLiveData<Int>()
    val nextButtonClicked: LiveData<Int>
        get() = _nextButtonClicked

    fun setNextButtonClicked(position: Int) {
        _nextButtonClicked.value = position
    }

    //holds the value of how many questions are answered correctly
    private val _answersCorrect = MutableLiveData<Int>()
    val answersCorrect: LiveData<Int>
        get() = _answersCorrect

    fun setAnswersCorrect(correct: Int) {
        _answersCorrect.value = correct
    }

    //set whether quiz nav host is visible or not
    private val _quizVisible = MutableLiveData<Boolean>()
    val quizVisible: LiveData<Boolean>
        get() = _quizVisible

    fun setQuizVisible(visible: Boolean) {
        _quizVisible.value = visible
    }

    //sets alpha value of the body of the article
    private val _articleBodyAlpha = MutableLiveData<Boolean>()
    val articleBodyAlpha: LiveData<Boolean>
        get() = _articleBodyAlpha

    fun setArticleBodyAlpha(visible: Boolean) {
        _articleBodyAlpha.value = visible
    }

    //calls database function to update activated value
    fun updateActivated(activated: Boolean, position: Int) {
        viewModelScope.launch {
            database.updateActivated(activated, position)
        }
    }

    init {
        _articleBodyAlpha.value = true
        _habitatDroppedDown.value = false
        _evolutionDroppedDown.value = false
        _fossilDroppedDown.value = false
    }

}

class DinoArticleViewModelFactory(
    private val dataSource: DinosaurEncyclopediaDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DinoArticleViewModel::class.java)) {
            return DinoArticleViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}