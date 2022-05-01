package com.example.dinoappv2.companionObjects

import androidx.lifecycle.MutableLiveData
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

//variables that can be seen by the whole app
class CompanionObject {
    companion object {
        //ONLY KEPT TO NOT CRASH APP (IN QUIZ FRAGMENT)
        var dinoArticleSelected: Int? = null
        //DELETE LATER
    }
}
