package com.example.dinoappv2.viewModels

import androidx.lifecycle.*
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.ProfileImage
import com.example.dinoappv2.databases.ProfileImageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val dataSource: ProfileImageDao
): ViewModel() {

    private var _currentDino: DinosaurEncyclopedia? = null
    val currentDino: DinosaurEncyclopedia?
        get() = _currentDino

    fun setCurrentDino(value: DinosaurEncyclopedia) {
        _currentDino = value
    }

    private val _dinoSelected = MutableLiveData<Boolean>()
    val dinoSelected: LiveData<Boolean>
        get() = _dinoSelected

    fun setDinoSelected(value: Boolean) {
        _dinoSelected.value = value
    }

    //changes image for the profile image
    fun insertProfileImage(image: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.insert(ProfileImage(image))
        }
    }
}

class ProfileEditViewModelFactory(
    private val dataSource: ProfileImageDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileEditViewModel::class.java)) {
            return ProfileEditViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}