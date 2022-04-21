package com.example.dinoappv2.viewModels

import androidx.lifecycle.*
import com.example.dinoappv2.BottomNavRepository
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import kotlinx.coroutines.launch

class EncyclopediaViewModel (repository: BottomNavRepository): ViewModel() {

    private lateinit var originalDinoList: List<DinosaurEncyclopedia>

    private val _allDinos = MutableLiveData<List<DinosaurEncyclopedia>>()
    val allDinos: LiveData<List<DinosaurEncyclopedia>>
        get() = _allDinos

    /** lets the user search through the dinosaurs in the encyclopedia **/
    fun filterDinosaurData(text: String?): Boolean {

        //if there is nothing typed in display all the dinos
        if(text == null || text == "") {
            _allDinos.value = originalDinoList
            return false
        }

        //return the dinos that match the start of the inputted text (not case sensitive)
        val length = text.length
        _allDinos.postValue(
            originalDinoList.filter { dino ->
                dino.name.take(length).lowercase() == text
            }
        )
        return true
    }

    init {
        viewModelScope.launch {
            //getting data from flow
            repository.getDinosaurData().collect {
                _allDinos.value = it
                originalDinoList = it
            }
        }
    }

}

class EncyclopediaViewModelFactory(
    private val repository: BottomNavRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EncyclopediaViewModel::class.java)) {
            return EncyclopediaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}