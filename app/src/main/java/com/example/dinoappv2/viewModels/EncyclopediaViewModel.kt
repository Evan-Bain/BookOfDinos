package com.example.dinoappv2.viewModels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia

class EncyclopediaViewModel(
    private val dinoNames: Array<String>
) : ViewModel() {

    @VisibleForTesting
    var originalDinoList = listOf<DinosaurEncyclopedia>()

    private val _filteredDinos = MutableLiveData<List<DinosaurEncyclopedia>>()
    val filteredDinos: LiveData<List<DinosaurEncyclopedia>>
        get() = _filteredDinos

    fun setDinoData(dinos: List<DinosaurEncyclopedia>) {
        originalDinoList = dinos
        _filteredDinos.value = dinos
    }

    /** lets the user search through the dinosaurs in the encyclopedia **/
    fun filterDinosaurData(text: String?): Boolean {

        //if there is nothing typed in display all the dinos
        if (text == null || text == "") {
            _filteredDinos.value = originalDinoList
            return false
        }

        //return the dinos that match the start of the inputted text (not case sensitive)
        val length = text.length
        _filteredDinos.value =
            originalDinoList.filter { dino ->
                dinoNames[dino.position].take(length).lowercase() == text.lowercase()
            }

        return true
    }
}

class EncyclopediaViewModelFactory(
    private val dinoNames: Array<String>,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EncyclopediaViewModel::class.java)) {
            return EncyclopediaViewModel(dinoNames) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}