package com.example.dinoappv2.viewModels

import androidx.lifecycle.*
import com.example.dinoappv2.dataClasses.BackgroundImage
import com.example.dinoappv2.databases.BackgroundImageDao
import com.example.dinoappv2.databases.BackgroundImageDatabase
import com.example.dinoappv2.viewModels.EncyclopediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectBackgroundViewModel : ViewModel() {

    //corresponding integer for background which is currently selected by user
    private val _backgroundSelected = MutableLiveData<Int>()
    val backgroundSelected: LiveData<Int>
        get() = _backgroundSelected

    fun setBackgroundSelected(value: Int) {
        _backgroundSelected.value = value
    }

    //handles whether or not check mark over background preview should disappear or reappear
    //if same background is being selected repeatedly
    private var _clickedAgain = false
    val clickedAgain: Boolean
        get() = _clickedAgain

    fun setClickedAgain(value: Boolean) {
        _clickedAgain = value
    }
}