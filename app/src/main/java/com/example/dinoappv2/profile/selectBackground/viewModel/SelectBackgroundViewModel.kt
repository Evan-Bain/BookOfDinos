package com.example.dinoappv2.profile.selectBackground.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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