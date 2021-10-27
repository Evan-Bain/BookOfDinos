package com.example.dinoappv2.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinoappv2.adapters.ProfileEditAdapter
import com.example.dinoappv2.dataClasses.ProfileImage
import com.example.dinoappv2.databases.ProfileImageDao
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val dataSource: ProfileImageDao
): ViewModel() {

    //save the state of the last and current clicked for configuration changes
    var lastPositionClicked = ArrayList<ProfileEditAdapter.ViewHolder>()
    var currentPosition: Int = -1

    //check if this is the first time the adapter is loading and not just a
    //configuration change
    var adapterRestarted = false

    //changes image for the profile image
    fun insertProfileImage(image: Int) {
        viewModelScope.launch {
            dataSource.insert(ProfileImage(0, image))
        }
    }
}

class ProfileEditViewModelFactory(
    private val dataSource: ProfileImageDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileEditViewModel::class.java)) {
            return ProfileEditViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}