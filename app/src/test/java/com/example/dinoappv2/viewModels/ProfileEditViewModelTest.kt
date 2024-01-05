package com.example.dinoappv2.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dinoappv2.MainCoroutineRule
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.dataClasses.ProfileImage
import com.example.dinoappv2.databases.FakeProfileImageDatasource
import com.example.dinoappv2.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileEditViewModelTest {

    // executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // set the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // subject under test
    private lateinit var profileEditViewModel: ProfileEditViewModel

    // fake datasource to be injected into view model
    private lateinit var fakeDatasource: FakeProfileImageDatasource

    @Before
    fun init() {
        // datasource with no profile image
        fakeDatasource = FakeProfileImageDatasource()

        // GIVEN - a fresh view model
        profileEditViewModel = ProfileEditViewModel(fakeDatasource)
    }

    @Test
    fun setCurrentDino_setAnkylosaurus_returnAnkylosaurus() {
        // GIVEN - ankylosaurus dinosaur data
        val ankylosaurus = DinosaurEncyclopedia(
            0,
            R.drawable.dino_badge_ankylosaurus,
            R.drawable.dino_badge_ankylosaurus_black,
            R.drawable.fb_ankylosaurus,
            R.drawable.fb_ankylosaurus_black,
            true
        )

        // WHEN - current dino is set to ankylosaurus
        profileEditViewModel.setCurrentDino(ankylosaurus)

        // THEN - current dino equals ankylosaurus
        assertThat(profileEditViewModel.currentDino, `is`(ankylosaurus))
    }

    @Test
    fun setDinoSelected_selectAndDeselectDino_returnTrueFalse() {
        // WHEN - dinosaur is selected
        profileEditViewModel.setDinoSelected(true)

        // THEN - dinoSelected equals true
        assertThat(profileEditViewModel.dinoSelected.getOrAwaitValue(), `is`(true))

        // WHEN - dinosaur is deselected
        profileEditViewModel.setDinoSelected(false)

        // THEN - dinoSelected equals false
        assertThat(profileEditViewModel.dinoSelected.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun insertProfileImage_insertAnkylosaurus_returnAnkylosaurus() {
        // WHEN - ankylosaurus badge image is inserted
        val ankylosaurusBadge = R.drawable.dino_badge_ankylosaurus
        runTest {
            profileEditViewModel.insertProfileImage(ankylosaurusBadge)
        }

        // THEN - profileImage in datasource is an ankylosaurus badge
        var profileImage: ProfileImage? = null
        runTest {
            profileImage = fakeDatasource.getImage().first()
        }
        assertThat(profileImage?.profileImage, `is`(ankylosaurusBadge))
    }
}