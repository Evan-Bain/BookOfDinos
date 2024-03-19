package com.example.dinoappv2.dictionary.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dinoappv2.dictionary.model.DictionaryStrings
import com.example.dinoappv2.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DictionaryViewModelTest {
    // executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // subject under test
    private lateinit var dictionaryViewModel: DictionaryViewModel

    @Before
    fun init() {
        // GIVEN - viewmodel with list of words
        dictionaryViewModel = DictionaryViewModel(wordList())
    }

    @Test
    fun filterDictionaryData_searchNone_returnAll() {
        // WHEN - user does not type in search bar
        dictionaryViewModel.filterDictionaryData(null)

        // THEN - all words are returned
        MatcherAssert.assertThat(
            dictionaryViewModel.allWords.getOrAwaitValue(),
            CoreMatchers.`is`(wordList())
        )
    }

    @Test
    fun filterDictionaryData_searchDne_returnEmptyList() {
        // WHEN - user types in "DNE"
        dictionaryViewModel.filterDictionaryData("DNE")

        // THEN - an empty list is returned
        MatcherAssert.assertThat(
            dictionaryViewModel.allWords.getOrAwaitValue(),
            CoreMatchers.`is`(emptyList())
        )
    }

    @Test
    fun filterDictionaryData_searchLowerA_returnAbilityAttract() {
        // WHEN - user types "a" in search bar
        dictionaryViewModel.filterDictionaryData("a")

        // THEN - "Ability" and "Attract" are returned
        val words = wordList().subList(0, 2)
        MatcherAssert.assertThat(
            dictionaryViewModel.allWords.getOrAwaitValue(),
            CoreMatchers.`is`(words)
        )
    }

    @Test
    fun filterDictionaryData_searchCapitalA_returnAbilityAttract() {
        // WHEN - user types "A" in search bar
        dictionaryViewModel.filterDictionaryData("A")

        // THEN - "Ability" and "Attract" are returned
        val words = wordList().subList(0, 2)
        MatcherAssert.assertThat(
            dictionaryViewModel.allWords.getOrAwaitValue(),
            CoreMatchers.`is`(words)
        )
    }

    @Test
    fun filterDictionaryData_searchWorldWarII_returnWorldWarII() {
        // WHEN - user types "World War II" in search bar
        dictionaryViewModel.filterDictionaryData("World War II")

        // THEN - "World War II" is returned
        val word: List<DictionaryStrings> = wordList().subList(9,10)
        MatcherAssert.assertThat(
            dictionaryViewModel.allWords.getOrAwaitValue(),
            CoreMatchers.`is`(word)
        )
    }
}

private fun wordList(list: List<DictionaryStrings>? = null): List<DictionaryStrings> {
    return list ?: listOf(
        DictionaryStrings("Ability", "First desc"),
        DictionaryStrings("Attract", "Second desc"),
        DictionaryStrings("Bison", "Third desc"),
        DictionaryStrings("Crab", "Fourth desc"),
        DictionaryStrings("Egypt", "Fifth desc"),
        DictionaryStrings("Fern", "Sixth desc"),
        DictionaryStrings("Giraffe", "Seventh desc"),
        DictionaryStrings("Injured", "Eighth desc"),
        DictionaryStrings("Laramidia", "Ninth desc"),
        DictionaryStrings("World War II", "Tenth desc"),
    )
}