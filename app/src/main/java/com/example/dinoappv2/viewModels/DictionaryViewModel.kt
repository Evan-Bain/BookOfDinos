package com.example.dinoappv2.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinoappv2.dataClasses.DictionaryStrings

class DictionaryViewModel(
    val originalWordList: List<DictionaryStrings>
) : ViewModel() {

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
                word.word.take(length).lowercase() == text.lowercase()
            }
        )

        return true
    }
}

class DictionaryViewModelFactory(
    private val originalWordList: List<DictionaryStrings>,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            return DictionaryViewModel(originalWordList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}