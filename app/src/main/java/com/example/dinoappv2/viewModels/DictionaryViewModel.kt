package com.example.dinoappv2.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinoappv2.dataClasses.DictionaryStrings
import kotlinx.coroutines.launch

class DictionaryViewModel : ViewModel() {

    private lateinit var originalWordList: List<DictionaryStrings>

    private val _allWords = MutableLiveData<List<DictionaryStrings>>()
    val allWords: LiveData<List<DictionaryStrings>>
        get() = _allWords

    /** lets the user search through the words in the dictionary **/
    fun filterDictionaryData(text: String?): Boolean {

        //if there is nothing typed in display all the words
        if(text == null || text == "") {
            _allWords.value = originalWordList
            return false
        }

        //return the words that match the start of the inputted text (not case sensitive)
        val length = text.length
        _allWords.postValue(
            originalWordList.filter { word ->
                word.word.take(length).lowercase() == text
            }
        )

        return true
    }

    init {
        viewModelScope.launch {
            DictionaryStrings.getDictionaryStrings().collect {
                _allWords.postValue(it)
                originalWordList = it
            }
        }
    }
}