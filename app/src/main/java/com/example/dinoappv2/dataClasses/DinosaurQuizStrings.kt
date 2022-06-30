package com.example.dinoappv2.dataClasses


//gets certain dino at correlating to the article selected
//NOTE: "NULL" is only for testing
fun getDinoStrings(position: Int): HashMap<Int, List<String>>? {
    return when (position) {
        8 -> getTrexQuizStrings()
        else -> null
    }
}
//NOTE: "NULL" is only for testing


//function that holds position of correct answers on radioGroup and returns them
fun getCorrectAnswers(position: Int): List<Int> {
    val correctAnswers = ArrayList<Int>()
    when (position) {
        0 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        1 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        2 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        3 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        4 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        5 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        6 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        7 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        8 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        9 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        10 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        11 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        12 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        13 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
        14 -> {
            correctAnswers.add(1)
            correctAnswers.add(0)
            correctAnswers.add(2)
            correctAnswers.add(3)
        }
    }
    return correctAnswers
}

//returns values of the strings required for each different question layout
private fun getTrexQuizStrings(): HashMap<Int, List<String>> {
    val hashMap = HashMap<Int, List<String>>()
    hashMap[-1] = listOf(
        "T-Rex Quiz"
    )
    hashMap[0] = listOf(
        "How strong is T-Rex's bite force",
        "What era did the T-Rex roam the Earth",
        "How tall can a T-Rex get",
        "Where was the first T-Rex fossil found"
    )
    hashMap[1] = listOf(
        "900 pounds",
        "12,800 pounds",
        "100,000 pounds",
        "55,600 pounds"
    )
    hashMap[2] = listOf(
        "Cretaceous",
        "Jurassic",
        "Mesozoic",
        "Triassic"
    )
    hashMap[3] = listOf(
        "25 feet",
        "9 feet",
        "13 feet",
        "50 feet"
    )
    hashMap[4] = listOf(
        "El Paso, Texas",
        "Caesar Creek, Ohio",
        "Big Bone Lick, Kentucky",
        "Hell Creek, Montana"
    )

    return hashMap
}

