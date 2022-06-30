package com.example.dinoappv2.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia

class EncyclopediaViewModel : ViewModel() {

    private var originalDinoList = listOf<DinosaurEncyclopedia>()

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
                dino.name.take(length).lowercase() == text
            }

        return true
    }
}