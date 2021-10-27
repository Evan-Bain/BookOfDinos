package com.example.dinoappv2.companionObjects

import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

//variables that can be seen by the whole app
class CompanionObject {
    companion object {
        //determine what recycler view item was pressed after BottomNavViewModel is destroyed
        var dinoArticleSelected: Int? = null
        var allDinos: List<DinosaurEncyclopedia>? = null
    }
}
