package com.example.dinoappv2.viewModels

import androidx.lifecycle.*
import com.example.dinoappv2.dataClasses.BackgroundImage
import com.example.dinoappv2.databases.BackgroundImageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val database: BackgroundImageDao
) : ViewModel() {

    //determines whether or not background should be set (to prevent app continuously being
    //recreated)
    private var _backgroundSet = false
    val backgroundSet: Boolean
        get() = _backgroundSet

    fun setBackground(value: Boolean) {
        _backgroundSet = value
    }

    //holds data for what current background selected is (stored in database and only
    //accessed when app launches)
    val backgroundImage: BackgroundImage?
        get() = database.getBackground()

    //holds data for what current background selected is (stored during runtime for quicker access)
    private val _backgroundSelected = MutableLiveData<Int>()
    val backgroundChanged: LiveData<Int>
        get() = _backgroundSelected

    /**indicate background has been changed during runtime**/
    fun changeBackground(value: Int) {
        _backgroundSelected.value = value
    }

    /**saves corresponding integer for a background to the BackgroundImageDatabase**/
    fun saveBackground(background: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            database.insert(
                BackgroundImage(background)
            )
        }
    }

    //set whether quiz is visible (in mainViewModel to override onNavigateUp & alter toolbar)
    private val _quizVisible = MutableLiveData(false)
    val quizVisible: LiveData<Boolean>
        get() = _quizVisible

    fun setQuizVisible(visible: Boolean) {
        _quizVisible.value = visible
    }

    private var _dictionaryWordSelected = false
    val dictionaryWordSelected: Boolean
        get() = _dictionaryWordSelected

    /** allows dictionary fragment to change enter transition **/
    fun setDictionaryWord(set: Boolean) {
        _dictionaryWordSelected = set
    }
}

class MainViewModelFactory(
    private val database: BackgroundImageDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}