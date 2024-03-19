package com.example.dinoappv2.encyclopedia.dinoArticle.model

import android.content.res.Resources
import com.example.dinoappv2.R
import kotlin.random.Random
import kotlin.random.nextInt

class DinosaurQuizStrings(
    private val resources: Resources,
    private val position: Int) {

    private val _correctAnswers = mutableListOf<String>()
    val correctAnswers: List<String>
        get() = _correctAnswers

    fun getQuizStrings(): HashMap<Int, List<String>> {
        val questions = getQuizQuestions()
        val answers = getQuizAnswers()

        val chosenQuestions = mutableListOf(-1, -1, -1, -1, -1)
        val hashMap = HashMap<Int, List<String>>()

        for(i in 0..4) {
            var random = Random.nextInt(0, 9)

            while(
                random == chosenQuestions[0] || random == chosenQuestions[1] ||
                random == chosenQuestions[2] || random == chosenQuestions[3] ||
                random == chosenQuestions[4]
            ) { random = Random.nextInt(0..9) }

            chosenQuestions.add(i, random)
            val correctAnswer = answers[random*4]
            _correctAnswers.add(correctAnswer)

            val shuffledAnswers = listOf(
                correctAnswer,
                answers[random*4 + 1],
                answers[random*4 + 2],
                answers[random*4 + 3],
            ).shuffled()

            hashMap[i] = listOf(
                questions[random],
                shuffledAnswers[0],
                shuffledAnswers[1],
                shuffledAnswers[2],
                shuffledAnswers[3],
            )
        }
        return hashMap
    }

    private fun getQuizQuestions(): Array<String> {
        return when(position) {
            0 -> resources.getStringArray(R.array.ankylosaurus_questions_array)
            1 -> resources.getStringArray(R.array.argentinosaurus_questions_array)
            2 -> resources.getStringArray(R.array.brontosaurus_questions_array)
            3 -> resources.getStringArray(R.array.dilophosaurus_questions_array)
            4 -> resources.getStringArray(R.array.giganotosaurus_questions_array)
            5 -> resources.getStringArray(R.array.leedsichthys_questions_array)
            6 -> resources.getStringArray(R.array.mosasaurus_questions_array)
            7 -> resources.getStringArray(R.array.pteranodon_questions_array)
            8 -> resources.getStringArray(R.array.quetzalcoatlus_questions_array)
            9 -> resources.getStringArray(R.array.spinosaurus_questions_array)
            10 -> resources.getStringArray(R.array.stegosaurus_questions_array)
            11 -> resources.getStringArray(R.array.therizinosaurus_questions_array)
            12 -> resources.getStringArray(R.array.triceratops_questions_array)
            13 -> resources.getStringArray(R.array.trex_questions_array)
            14 -> resources.getStringArray(R.array.velociraptor_questions_array)
            else -> arrayOf()
        }
    }

    private fun getQuizAnswers(): Array<String> {
        return when(position) {
            0 -> resources.getStringArray(R.array.ankylosaurus_answers_array)
            1 -> resources.getStringArray(R.array.argentinosaurus_answers_array)
            2 -> resources.getStringArray(R.array.brontosaurus_answers_array)
            3 -> resources.getStringArray(R.array.dilophosaurus_answers_array)
            4 -> resources.getStringArray(R.array.giganotosaurus_answers_array)
            5 -> resources.getStringArray(R.array.leedsichthys_answers_array)
            6 -> resources.getStringArray(R.array.mosasaurus_answers_array)
            7 -> resources.getStringArray(R.array.pteranodon_answers_array)
            8 -> resources.getStringArray(R.array.quetzalcoatlus_answers_array)
            9 -> resources.getStringArray(R.array.spinosaurus_answers_array)
            10 -> resources.getStringArray(R.array.stegosaurus_answers_array)
            11 -> resources.getStringArray(R.array.therizinosaurus_answers_array)
            12 -> resources.getStringArray(R.array.triceratops_answers_array)
            13 -> resources.getStringArray(R.array.trex_answers_array)
            14 -> resources.getStringArray(R.array.velociraptor_answers_array)
            else -> arrayOf()
        }
    }
}

