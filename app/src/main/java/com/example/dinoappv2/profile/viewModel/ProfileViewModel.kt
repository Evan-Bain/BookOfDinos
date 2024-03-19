package com.example.dinoappv2.profile.viewModel

import androidx.lifecycle.*
import com.example.dinoappv2.encyclopedia.model.DinosaurEncyclopedia
import com.example.dinoappv2.widget.model.WidgetData
import com.example.dinoappv2.profile.profileEdit.model.fromPosition
import com.example.dinoappv2.profile.profileEdit.model.ProfileImageDao
import com.example.dinoappv2.widget.model.WidgetDataDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileDatasource: ProfileImageDao,
    private val widgetDataSource: WidgetDataDao
) : ViewModel() {

    //data for every dino in the app
    private val allDinos = MutableLiveData<List<DinosaurEncyclopedia>>()

    fun setAllDinos(dinos: List<DinosaurEncyclopedia>) {
        allDinos.value = dinos
    }

    //progress to be set in progress bar
    val levelProgress = Transformations.map(allDinos) {
        val activated = it.filter { dino -> dino.activated }.size

        return@map if(activated == 15) {
            //if max level: show progress complete
            5
        } else {
            //max progress is 4
            //min progress is 1 unless on first level
            activated % 5
        }
    }

    val currentLevel = Transformations.switchMap(allDinos) {
        calculateLevel(it)
    }

    //data for all widgets currently on home screen
    private val _allWidgets = MutableLiveData<List<WidgetData>>()
    val allWidgets: LiveData<List<WidgetData>>
        get() = _allWidgets

    //resource id for what image is selected as the profile image
    private val _profileImage = MutableLiveData<Int>()
    val profileImage: LiveData<Int>
        get() = _profileImage

    /** calculates current level of user from the number of quizzes completed **/
    private fun calculateLevel(dinoList: List<DinosaurEncyclopedia>): LiveData<Int> {
        val level = MutableLiveData<Int>()
        level.value = (dinoList.filter { it.activated }.size) / 5
        return level
    }

    init {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                //retrieves resource id for image selected as profile image
                profileDatasource.getImage().collect {
                    _profileImage.postValue(it.fromPosition())
                }
            }
            launch(Dispatchers.IO) {
                widgetDataSource.getWidgets().collect {
                    //retrieves all widgets on the home screen
                    _allWidgets.postValue(it)
                }
            }
        }
    }
}

class ProfileViewModelFactory(
    private val profileDatasource: ProfileImageDao,
    private val widgetDataSource: WidgetDataDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(profileDatasource, widgetDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}