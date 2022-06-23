package com.example.dinoappv2.viewModels

import androidx.lifecycle.*
import com.example.dinoappv2.BottomNavRepository
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.WidgetData
import com.example.dinoappv2.dataClasses.fromPosition
import com.example.dinoappv2.databases.ProfileImageDao
import com.example.dinoappv2.databases.WidgetDataDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val dataSource: BottomNavRepository,
    private val profileDatasource: ProfileImageDao,
    private val widgetDataSource: WidgetDataDao): ViewModel() {

    //data for every dino in the app
    private val _allDinos = MutableLiveData<List<DinosaurEncyclopedia>>()
    val allDinos: LiveData<List<DinosaurEncyclopedia>>
        get() = _allDinos

    //data for all widgets currently on home screen
    private val _allWidgets = MutableLiveData<List<WidgetData>>()
    val allWidgets: LiveData<List<WidgetData>>
        get() = _allWidgets

    //resource id for what image is selected as the profile image
    private val _profileImage = MutableLiveData<Int>()
    val profileImage: LiveData<Int>
        get() = _profileImage

    val currentLevel = Transformations.switchMap(allDinos) {
        calculateLevel(it)
    }

    /**calculates current level of user from the number of quizzes completed**/
    private fun calculateLevel(dinoList: List<DinosaurEncyclopedia>): LiveData<Int> {
        val level = MutableLiveData<Int>()
        level.value = ((dinoList.filter { it.activated == true }.size * 10) / 50)
        return level
    }

    init {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                //retrieves relevant data of the dinosaurs in the app
                dataSource.getDinosaurData().collect {
                    _allDinos.postValue(it)
                }
            }
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
    private val dataSource: BottomNavRepository,
    private val profileDatasource: ProfileImageDao,
    private val widgetDataSource: WidgetDataDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(dataSource, profileDatasource, widgetDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}