package com.example.dinoappv2.encyclopedia.dinoArticle.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dinoappv2.MainCoroutineRule
import com.example.dinoappv2.R
import com.example.dinoappv2.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DinoArticleViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var dinoArticleViewModel: DinoArticleViewModel

    @Before
    fun init() {
        // GIVEN - a fresh view model
        dinoArticleViewModel = DinoArticleViewModel()
    }

    @Test
    fun habitatDropDownClicked_openCloseOpen_returnTrueFalseTrue() {
        // WHEN - habitat drop down is opened
        dinoArticleViewModel.habitatDropDownClicked()
        // THEN - habitatDroppedDown equals true
        assertThat(dinoArticleViewModel.habitatDroppedDown.getOrAwaitValue(), `is`(true))

        // WHEN - habitat drop down is closed
        dinoArticleViewModel.habitatDropDownClicked()
        // THEN - habitatDroppedDown equals false
        assertThat(dinoArticleViewModel.habitatDroppedDown.getOrAwaitValue(), `is`(false))

        // WHEN - habitat dropped down is opened
        dinoArticleViewModel.habitatDropDownClicked()
        // THEN - habitatDroppedDown equals true
        assertThat(dinoArticleViewModel.habitatDroppedDown.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun evolutionDropDownClicked_openCloseOpen_returnTrueFalseTrue() {
        // WHEN - evolution drop down is opened
        dinoArticleViewModel.evolutionDropDownClicked()
        // THEN - evolutionDroppedDown equals true
        assertThat(dinoArticleViewModel.evolutionDroppedDown.getOrAwaitValue(), `is`(true))

        // WHEN - evolution drop down is closed
        dinoArticleViewModel.evolutionDropDownClicked()
        // THEN - evolutionDroppedDown equals false
        assertThat(dinoArticleViewModel.evolutionDroppedDown.getOrAwaitValue(), `is`(false))

        // WHEN - evolution dropped down is opened
        dinoArticleViewModel.evolutionDropDownClicked()
        // THEN - evolutionDroppedDown equals true
        assertThat(dinoArticleViewModel.evolutionDroppedDown.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun fossilDropDownClicked_openCloseOpen_returnTrueFalseTrue() {
        // WHEN - fossil drop down is opened
        dinoArticleViewModel.fossilDropDownClicked()
        // THEN - fossilDroppedDown equals true
        assertThat(dinoArticleViewModel.fossilDroppedDown.getOrAwaitValue(), `is`(true))

        // WHEN - fossil drop down is closed
        dinoArticleViewModel.fossilDropDownClicked()
        // THEN - fossilDroppedDown equals false
        assertThat(dinoArticleViewModel.fossilDroppedDown.getOrAwaitValue(), `is`(false))

        // WHEN - fossil dropped down is opened
        dinoArticleViewModel.fossilDropDownClicked()
        // THEN - fossilDroppedDown equals true
        assertThat(dinoArticleViewModel.fossilDroppedDown.getOrAwaitValue(), `is`(true))
    }

    // parameters setCurrentAudio():
    // -1 - disable
    // 0 - story
    // 1 - habitat
    // 2 - evolution
    // 3 - fossil

    @Test
    fun setCurrentAudio_disableAudio_returnNegativeOne() {
        // WHEN - audio is disabled
        dinoArticleViewModel.setCurrentAudio(-1)

        // THEN - currentAudio is equal to -1
        assertThat(dinoArticleViewModel.currentAudio, `is`(-1))
    }

    @Test
    fun setCurrentAudio_playStory_returnZero() {
        // WHEN - story audio is played
        dinoArticleViewModel.setCurrentAudio(0)

        // THEN - currentAudio is equal to 0
        assertThat(dinoArticleViewModel.currentAudio, `is`(0))
    }

    @Test
    fun setCurrentAudio_playHabitat_returnOne() {
        // WHEN - habitat audio is played
        dinoArticleViewModel.setCurrentAudio(1)

        // THEN - currentAudio is equal to 1
        assertThat(dinoArticleViewModel.currentAudio, `is`(1))
    }

    @Test
    fun setCurrentAudio_playEvolution_returnTwo() {
        // WHEN - evolution audio is played
        dinoArticleViewModel.setCurrentAudio(2)

        // THEN - currentAudio is equal to 2
        assertThat(dinoArticleViewModel.currentAudio, `is`(2))
    }

    @Test
    fun setCurrentAudio_playFossil_returnThree() {
        // WHEN - fossil audio is played
        dinoArticleViewModel.setCurrentAudio(3)

        // THEN - currentAudio is equal to 3
        assertThat(dinoArticleViewModel.currentAudio, `is`(3))
    }

    @Test
    fun setCurrentAudio_error_returnNull() {
        // WHEN - error occurs
        dinoArticleViewModel.setCurrentAudio(null)

        // THEN - null is returned
        assertThat(dinoArticleViewModel.currentAudio, `is`(nullValue()))
    }

    @Test
    fun setCurrentAudio_playAll_returnAllAndAlternateResetSub() {
        // WHEN - each audio is played
        // THEN - currentAudio returns respective number along with
        // resetSubtitles changing value each time called
        var resetSub: Boolean? = null

        dinoArticleViewModel.setCurrentAudio(-1)
        assertThat(dinoArticleViewModel.currentAudio, `is`(-1))
        assertThat(dinoArticleViewModel.resetSubtitles.getOrAwaitValue(), `is`(not(resetSub)))
        resetSub = dinoArticleViewModel.resetSubtitles.value

        dinoArticleViewModel.setCurrentAudio(0)
        assertThat(dinoArticleViewModel.currentAudio, `is`(0))
        assertThat(dinoArticleViewModel.resetSubtitles.getOrAwaitValue(), `is`(not(resetSub)))
        resetSub = dinoArticleViewModel.resetSubtitles.value

        dinoArticleViewModel.setCurrentAudio(1)
        assertThat(dinoArticleViewModel.currentAudio, `is`(1))
        assertThat(dinoArticleViewModel.resetSubtitles.getOrAwaitValue(), `is`(not(resetSub)))
        resetSub = dinoArticleViewModel.resetSubtitles.value

        dinoArticleViewModel.setCurrentAudio(2)
        assertThat(dinoArticleViewModel.currentAudio, `is`(2))
        assertThat(dinoArticleViewModel.resetSubtitles.getOrAwaitValue(), `is`(not(resetSub)))
        resetSub = dinoArticleViewModel.resetSubtitles.value

        dinoArticleViewModel.setCurrentAudio(3)
        assertThat(dinoArticleViewModel.currentAudio, `is`(3))
        assertThat(dinoArticleViewModel.resetSubtitles.getOrAwaitValue(), `is`(not(resetSub)))
    }

    @Test
    fun setRadioButtonClicked_firstRadioClicked_returnZeroNextEnabled() {
        // WHEN - first radio button is clicked
        dinoArticleViewModel.setRadioButtonClicked(R.id.quiz_radio_button_0)

        // THEN - radioButtonClicked equals zero
        // // and nextButtonEnabled equals true
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(0))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun setRadioButtonClicked_secondRadioClicked_returnOneNextEnabled() {
        // WHEN - second radio button is clicked
        dinoArticleViewModel.setRadioButtonClicked(R.id.quiz_radio_button_1)

        // THEN - radioButtonClicked equals one
        // and nextButtonEnabled equals true
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(1))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun setRadioButtonClicked_thirdRadioClicked_returnTwoNextEnabled() {
        // WHEN - third radio button is clicked
        dinoArticleViewModel.setRadioButtonClicked(R.id.quiz_radio_button_2)

        // THEN - radioButtonClicked equals two
        // and nextButtonEnabled equals true
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(2))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun setRadioButtonClicked_fourthRadioClicked_returnThreeNextEnabled() {
        // WHEN - fourth radio button is clicked
        dinoArticleViewModel.setRadioButtonClicked(R.id.quiz_radio_button_3)

        // THEN - radioButtonClicked equals three
        // and nextButtonEnabled equals true
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(3))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun setRadioButtonClicked_error_returnNegativeOneNextDisabled() {
        // WHEN - an error has occurred
        dinoArticleViewModel.setRadioButtonClicked(99)

        // THEN - radioButtonClicked equals -1
        // and nextButtonEnabled equals false
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(-1))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun setRadioButtonClicked_clickedMultiple_returnAppropriateUpdates() {
        // WHEN - multiple radio buttons are selected
        // THEN - the respective values are returned

        dinoArticleViewModel.setRadioButtonClicked(99)
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(-1))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(false))

        dinoArticleViewModel.setRadioButtonClicked(R.id.quiz_radio_button_2)
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(2))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(true))

        dinoArticleViewModel.setRadioButtonClicked(R.id.quiz_radio_button_0)
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(0))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(true))

        dinoArticleViewModel.setRadioButtonClicked(R.id.quiz_radio_button_1)
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(1))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(true))

        dinoArticleViewModel.setRadioButtonClicked(0)
        assertThat(dinoArticleViewModel.radioButtonClicked.getOrAwaitValue(), `is`(-1))
        assertThat(dinoArticleViewModel.nextButtonEnabled.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun nextButtonClicked_buttonClicked_returnsOne() {
        // WHEN - user clicks the next button
        dinoArticleViewModel.nextButtonClicked(-1)

        // THEN - nextButtonClicked equals 1
        assertThat(dinoArticleViewModel.nextButtonClicked.getOrAwaitValue(), `is`(1))
    }

    @Test
    fun nextButtonClicked_buttonClickedThreeTimes_returnsThree() {
        // WHEN - user clicks the next button three times
        for (i in 0..2)
            dinoArticleViewModel.nextButtonClicked(-1)

        // THEN - nextButtonClicked equals 3
        assertThat(dinoArticleViewModel.nextButtonClicked.getOrAwaitValue(), `is`(3))
    }

    @Test
    fun nextButtonClicked_quizClosed_returnsNull() {
        // WHEN - user closes quiz
        dinoArticleViewModel.nextButtonClicked(0)

        // THEN - nextButtonClicked equals null
        assertThat(dinoArticleViewModel.nextButtonClicked.getOrAwaitValue(), `is`(nullValue()))
    }

    @Test
    fun nextButtonClicked_openedQuizAlreadyPassed_returnsFiveAndSetsAnswersCorrect() {
        // WHEN - user opens quiz which was previously passed
        dinoArticleViewModel.nextButtonClicked(5)

        // THEN - nextButtonClicked equals five
        // and answersCorrect equals five
        assertThat(dinoArticleViewModel.answersCorrect, `is`(5))
        assertThat(dinoArticleViewModel.nextButtonClicked.getOrAwaitValue(), `is`(5))
    }

    @Test
    fun setQuizStringAnswers_ankylosaurusQuiz_returnAnkylosaurusStrings() {
        // GIVEN - list of ankylosaurus strings
        val ankyQuestions = getQuizQuestions()
        val ankyAnswers = getQuizAnswers()
        val ankyStrings = listOf(
            ankyQuestions[0],
            ankyAnswers[0],
            ankyAnswers[1],
            ankyAnswers[2],
            ankyAnswers[3],
        )

        // WHEN - strings are passed to setQuizStringAnswers
        dinoArticleViewModel.setQuizStringAnswers(ankyStrings)

        // THEN - quizQuestion and each answer is mapped to its respective value
        assertThat(dinoArticleViewModel.quizQuestion.getOrAwaitValue(), `is`(ankyQuestions[0]))
        assertThat(dinoArticleViewModel.firstAnswer.getOrAwaitValue(), `is`(ankyAnswers[0]))
        assertThat(dinoArticleViewModel.secondAnswer.getOrAwaitValue(), `is`(ankyAnswers[1]))
        assertThat(dinoArticleViewModel.thirdAnswer.getOrAwaitValue(), `is`(ankyAnswers[2]))
        assertThat(dinoArticleViewModel.fourthAnswer.getOrAwaitValue(), `is`(ankyAnswers[3]))
    }

    @Test
    fun updateAnswersCorrect_twoCorrectAnswers_returnTwoCorrectAnswers() {
        // WHEN - two answers are answered correctly
        for(i in 0..1)
            dinoArticleViewModel.updateAnswerCorrect()

        // THEN - answersCorrect equals two
        assertThat(dinoArticleViewModel.answersCorrect, `is`(2))
    }

    @Test
    fun resetAnswersCorrect_threeCorrectAnswersAndReset_returnZeroCorrectAnswers() {
        // WHEN - two answers are answered correctly
        for(i in 0..2)
            dinoArticleViewModel.updateAnswerCorrect()

        dinoArticleViewModel.resetAnswersCorrect()

        // THEN - answersCorrect equals two
        assertThat(dinoArticleViewModel.answersCorrect, `is`(0))
    }

    @Test
    fun setResultText_fourCorrect_messagesForPassingAndEightyPercent() {
         // GIVEN - four answers are correct
        for(i in 0..3)
            dinoArticleViewModel.updateAnswerCorrect()

        // WHEN - next button is clicked, calling setResultText
        dinoArticleViewModel.nextButtonClicked(-1)

        // THEN - percentText displays 80% and messages texts displays messages for passing
        assertThat(dinoArticleViewModel.resultMessageText.getOrAwaitValue(), `is`("Congratulations"))
        assertThat(dinoArticleViewModel.resultText.getOrAwaitValue(), `is`("You Passed"))
        assertThat(dinoArticleViewModel.percentText.getOrAwaitValue(), `is`("80%"))
    }

    @Test
    fun setResultText_threeCorrect_messagesForFailingAndSixtyPercent() {
        // GIVEN - four answers are correct
        for(i in 0..2)
            dinoArticleViewModel.updateAnswerCorrect()

        // WHEN - next button is clicked, calling setResultText
        dinoArticleViewModel.nextButtonClicked(-1)

        // THEN - percentText displays 80% and messages texts displays messages for passing
        assertThat(dinoArticleViewModel.resultMessageText.getOrAwaitValue(), `is`("Unfortunately"))
        assertThat(dinoArticleViewModel.resultText.getOrAwaitValue(), `is`("You Failed"))
        assertThat(dinoArticleViewModel.percentText.getOrAwaitValue(), `is`("60%"))
    }
}

// stub (test dummy) for getting quiz questions (for ankylosaurus)
private fun getQuizQuestions(): Array<String> {
    return arrayOf(
        "What period id Ankylosaurus live in?",
        "How many Ankylosaurus fossils have been found?",
        "Where was the first Ankylosaurus fossil found?",
    )
}

// stub (test dummy) for getting quiz answers (for ankylosaurus)
private fun getQuizAnswers(): Array<String> {
    return arrayOf(
        "Cretaceous",
        "Triassic",
        "Jurassic",
        "Neogene",
        "3",
        "10",
        "1",
        "34"
    )
}