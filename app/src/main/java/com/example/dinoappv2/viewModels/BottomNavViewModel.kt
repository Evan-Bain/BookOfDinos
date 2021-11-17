package com.example.dinoappv2.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinoappv2.R
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.ProfileImage
import com.example.dinoappv2.databases.DinosaurEncyclopediaDao
import com.example.dinoappv2.databases.ProfileImageDao
import kotlinx.coroutines.launch

class BottomNavViewModel(
    private val dinoDatabase: DinosaurEncyclopediaDao,
    val profileDatabase: ProfileImageDao): ViewModel() {

    //used for fragments to access data from database
    var allDinos = ArrayList<DinosaurEncyclopedia>()

    //assigns variable "allDinos" to all entities of database so they can be accessed
    //throughout app
    private fun getAll() {
        viewModelScope.launch {
            if(dinoDatabase.getAll().size != 10) {
                dinoDatabase.insert(DinosaurEncyclopedia(0, R.string.ankylosaurus,
                    R.drawable.dino_badge_ankylosaurus, R.drawable.dino_badge_ankylosaurus_black,false))
                dinoDatabase.insert(DinosaurEncyclopedia(1, R.string.brontosaurus,
                    R.drawable.dino_badge_brontosaurus, R.drawable.dino_badge_brontosaurus_black,false))
                dinoDatabase.insert(DinosaurEncyclopedia(2, R.string.dilophosaurus,
                    R.drawable.dino_badge_dilophosaurus, R.drawable.dino_badge_dilophosaurus_black,false))
                dinoDatabase.insert(DinosaurEncyclopedia(3, R.string.mosasaurus,
                    R.drawable.dino_badge_mosasaurus, R.drawable.dino_badge_mosasaurus_black,false))
                dinoDatabase.insert(DinosaurEncyclopedia(4, R.string.pterodactyl,
                    R.drawable.dino_badge_pterodactyl, R.drawable.dino_badge_pterodactyl_black,false))
                dinoDatabase.insert(DinosaurEncyclopedia(5, R.string.spinosaurus,
                    R.drawable.dino_badge_spinosaurus, R.drawable.dino_badge_spinosaurus_black, false))
                dinoDatabase.insert(DinosaurEncyclopedia(6, R.string.stegosaurus,
                    R.drawable.dino_badge_stegasaurus, R.drawable.dino_badge_stegasaurus_black, false))
                dinoDatabase.insert(DinosaurEncyclopedia(7, R.string.triceratops,
                    R.drawable.dino_badge_triceratops, R.drawable.dino_badge_triceratops_black, false))
                dinoDatabase.insert(DinosaurEncyclopedia(8, R.string.t_rex,
                    R.drawable.dino_badge_trex, R.drawable.dino_badge_trex_black, false))
                dinoDatabase.insert(DinosaurEncyclopedia(9, R.string.velociraptor,
                    R.drawable.dino_badge_raptor, R.drawable.dino_badge_raptor_black, false))
                dinoDatabase.getAll()
            }
            allDinos = dinoDatabase.getAll() as ArrayList<DinosaurEncyclopedia>
            CompanionObject.allDinos = allDinos
        }
    }

    init {
        //DELETE LATER
        viewModelScope.launch {
            //dinoDatabase.deleteAll()
            //sets the default profile image for first time loading app
            profileDatabase.insertFirst(ProfileImage(0, R.drawable.profile_icon))
        }
        //DELETE LATER
        //receives all objects in database to ensure each launch of app shows latest data
        getAll()
    }

}

class EncyclopediaViewModelFactory(
    private val dinoDataSource: DinosaurEncyclopediaDao,
    private val profileDataSource: ProfileImageDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomNavViewModel::class.java)) {
            return BottomNavViewModel(dinoDataSource, profileDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}