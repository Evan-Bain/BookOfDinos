package com.example.dinoappv2.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dinoappv2.MainCoroutineRule
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EncyclopediaViewModelTest {

    // executes each task synchronously using architecture components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // set the main coroutine for unit testing
    @get:Rule
    var mainCloseable = MainCoroutineRule()

    private lateinit var encyclopediaViewModel: EncyclopediaViewModel

    @Before
    fun init() {
        // GIVEN - names of dinosaurs
        encyclopediaViewModel = EncyclopediaViewModel(namesArray())
    }

    @Test
    fun setDinoData_setDino_returnData() {
        // GIVEN - list of dinosaurs
        val dinos = listOf(
            DinosaurEncyclopedia(0,0,0,0,0,false),
            DinosaurEncyclopedia(1,1,1,1,1,false),
            DinosaurEncyclopedia(0,1,2,3,4,true)
        )

        // WHEN - originalDinoList and filteredDinos are assigned to list
        encyclopediaViewModel.setDinoData(dinos)

        // THEN - originalDinoList and filteredDinos contain list
        assertThat(encyclopediaViewModel.originalDinoList, `is`(dinos))
        assertThat(encyclopediaViewModel.filteredDinos.getOrAwaitValue(), `is`(dinos))
    }

    @Test
    fun setDinoData_emptyList_returnEmptyList() {
        // GIVEN - empty list
        // WHEN - originalDinoList and filteredDinos are assigned to list
        encyclopediaViewModel.setDinoData(emptyList())

        // THEN - originalDinoList and filteredDinos contain empty list
        assertThat(encyclopediaViewModel.originalDinoList, `is`(emptyList()))
        assertThat(encyclopediaViewModel.filteredDinos.getOrAwaitValue(), `is`(emptyList()))
    }

    @Test
    fun filterDinosaurData_searchNone_returnAll() {
        // GIVEN - list of dinosaurs
        encyclopediaViewModel.setDinoData(getDinos())

        // WHEN - user does not type in search bar
        encyclopediaViewModel.filterDinosaurData(null)

        // THEN - all dinos are returned
        assertThat(encyclopediaViewModel.filteredDinos.getOrAwaitValue(), `is`(getDinos()))
    }

    @Test
    fun filterDinosaurData_searchDne_returnEmptyList() {
        // GIVEN - list of dinosaurs
        encyclopediaViewModel.setDinoData(getDinos())

        // WHEN - user searches for a dino that doesn't exit
        encyclopediaViewModel.filterDinosaurData("Indominus Rex")

        // THEN - an empty list is returned
        assertThat(encyclopediaViewModel.filteredDinos.getOrAwaitValue(), `is`(emptyList()))
    }

    @Test
    fun filterDinosaurData_searchLowerA_returnAnkArg() {
        // GIVEN - list of dinosaurs
        encyclopediaViewModel.setDinoData(getDinos())

        // WHEN - user searches "a"
        encyclopediaViewModel.filterDinosaurData("a")

        // THEN - "Ankylosaurus" and "Argentinosaurus" are returned
        val dinos = getDinos().subList(0, 2)
        assertThat(encyclopediaViewModel.filteredDinos.getOrAwaitValue(), `is`(dinos))
    }

    @Test
    fun filterDinosaurData_searchCapitalA_returnAnkArg() {
        // GIVEN - list of dinosaurs
        encyclopediaViewModel.setDinoData(getDinos())

        // WHEN - user searches "A"
        encyclopediaViewModel.filterDinosaurData("A")

        // THEN - "Ankylosaurus" and "Argentinosaurus" are returned
        val dinos = getDinos().subList(0, 2)
        assertThat(encyclopediaViewModel.filteredDinos.getOrAwaitValue(), `is`(dinos))
    }

    @Test
    fun filterDinosaurData_searchTDashRex_returnTDashRex() {
        // GIVEN - list of dinosaurs
        encyclopediaViewModel.setDinoData(getDinos())

        // WHEN - user searches "T-Rex"
        encyclopediaViewModel.filterDinosaurData("T-Rex")

        val dino = getDinos().subList(13, 14)
        assertThat(encyclopediaViewModel.filteredDinos.getOrAwaitValue(), `is`(dino))
    }
}

private fun namesArray(names: Array<String>? = null): Array<String> {
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

private fun getDinos(dinos: List<DinosaurEncyclopedia>? = null): List<DinosaurEncyclopedia> {
    return dinos ?:  listOf(
        DinosaurEncyclopedia(
            0,
            R.drawable.dino_badge_ankylosaurus,
            R.drawable.dino_badge_ankylosaurus_black,
            R.drawable.fb_ankylosaurus,
            R.drawable.fb_ankylosaurus_black,
            false
        ),
        //#2
        DinosaurEncyclopedia(
            1,
            R.drawable.dino_badge_argentinosaurus,
            R.drawable.dino_badge_argentinosaurus_black,
            R.drawable.fb_argentinosaurus,
            R.drawable.fb_argentinosaurus_black,
            false
        ),
        //#3
        DinosaurEncyclopedia(
            2,
            R.drawable.dino_badge_brontosaurus,
            R.drawable.dino_badge_brontosaurus_black,
            R.drawable.fb_brontosaurus,
            R.drawable.fb_brontosaurus_black,
            false
        ),
        //#4
        DinosaurEncyclopedia(
            3,
            R.drawable.dino_badge_dilophosaurus,
            R.drawable.dino_badge_dilophosaurus_black,
            R.drawable.fb_dilophosaurus,
            R.drawable.fb_dilophosarus_black,
            false
        ),
        //#5
        DinosaurEncyclopedia(
            4,
            R.drawable.dino_badge_giganotosaurus,
            R.drawable.dino_badge_giganotosaurus_black,
            R.drawable.fb_giganotosaurus,
            R.drawable.fb_giganotosaurus_black,
            false
        ),
        //#6
        DinosaurEncyclopedia(
            5,
            R.drawable.dino_badge_leedsichthys,
            R.drawable.dino_badge_leedsichthys_black,
            R.drawable.fb_leedsichthys,
            R.drawable.fb_leedsichthys_black,
            false
        ),
        //#7
        DinosaurEncyclopedia(
            6,
            R.drawable.dino_badge_mosasaurus,
            R.drawable.dino_badge_mosasaurus_black,
            R.drawable.fb_mosasaurus,
            R.drawable.fb_mosasaurus_black,
            false
        ),
        //#8
        DinosaurEncyclopedia(
            7,
            R.drawable.dino_badge_pteranodon,
            R.drawable.dino_badge_pteranodon_black,
            R.drawable.fb_pteranodon,
            R.drawable.fb_pteranodon_black,
            false
        ),
        //#9
        DinosaurEncyclopedia(
            8,
            R.drawable.dino_badge_quetzalcoatlus,
            R.drawable.dino_badge_quetzalcoatlus_black,
            R.drawable.fb_quetzalcoatlus,
            R.drawable.fb_quetzalcoatlus_black,
            false
        ),
        //#10
        DinosaurEncyclopedia(
            9,
            R.drawable.dino_badge_spinosaurus,
            R.drawable.dino_badge_spinosaurus_black,
            R.drawable.fb_spinosaurus,
            R.drawable.fb_spinosaurus_black,
            false
        ),
        //#11
        DinosaurEncyclopedia(
            10,
            R.drawable.dino_badge_stegosaurus,
            R.drawable.dino_badge_stegosaurus_black,
            R.drawable.fb_stegosaurus,
            R.drawable.fb_stegosaurus_black,
            false
        ),
        //#12
        DinosaurEncyclopedia(
            11,
            R.drawable.dino_badge_therizinosaurus,
            R.drawable.dino_badge_therizinosaurus_black,
            R.drawable.fb_therizinosaurus,
            R.drawable.fb_therizinosaurus_black,
            false
        ),
        //#13
        DinosaurEncyclopedia(
            12,
            R.drawable.dino_badge_triceratops,
            R.drawable.dino_badge_triceratops_black,
            R.drawable.fb_triceratops,
            R.drawable.fb_triceratops_black,
            false
        ),
        //#14
        DinosaurEncyclopedia(
            13,
            R.drawable.dino_badge_trex,
            R.drawable.dino_badge_trex_black,
            R.drawable.fb_trex,
            R.drawable.fb_trex_black,
            false
        ),
        //#15
        DinosaurEncyclopedia(
            14,
            R.drawable.dino_badge_velociraptor,
            R.drawable.dino_badge_velociraptor_black,
            R.drawable.fb_velociraptor,
            R.drawable.fb_velociraptor_black,
            false
        )
    )
}