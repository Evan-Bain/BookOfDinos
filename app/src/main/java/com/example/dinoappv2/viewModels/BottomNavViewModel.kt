package com.example.dinoappv2.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinoappv2.BottomNavRepository
import com.example.dinoappv2.R
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.ProfileImage
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import com.example.dinoappv2.databases.ProfileImageDao
import kotlinx.coroutines.launch

class BottomNavViewModel(private val repository: BottomNavRepository): ViewModel() {

    //used for fragments to access data from database
    var allDinos = listOf<DinosaurEncyclopedia>()

    //assigns variable "allDinos" to all entities of database so they can be accessed
    //throughout app
    private fun getAll() {
        viewModelScope.launch {
            allDinos = repository.getDinosaurData()
            CompanionObject.allDinos = allDinos
        }
    }

    init {
        getAll()
    }

}

class EncyclopediaViewModelFactory(
    private val repository: BottomNavRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomNavViewModel::class.java)) {
            return BottomNavViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}