package com.example.dinoappv2.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EncyclopediaViewModelTest() {

    // executes each task synchronously using architecture components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var encyclopediaViewModel: EncyclopediaViewModel

    @Before
    fun init() {
        encyclopediaViewModel = EncyclopediaViewModel(namesArray())
    }
}

fun namesArray(names: Array<String>? = null): Array<String> {
    return names ?: arrayOf(
        "Ankylosaurus",
        "Argentinosaurus",
        "Brontosaurus",
        "Dilophosaurus",
        "Giganotosaurus",
        "Leedsichthys",
        "Mosasaurus",
        "Pteranodon",
        "Quetzalcoatlus",
        "Spinosaurus",
        "Stegosaurus",
        "Therizinosaurus",
        "Triceratops",
        "T-Rex",
        "Velociraptor"
    )
}