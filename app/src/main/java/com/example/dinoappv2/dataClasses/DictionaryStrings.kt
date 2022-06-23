package com.example.dinoappv2.dataClasses

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class DictionaryStrings(
    val word: String,
    val definition: String
) {
    companion object {
        /** Returns words and correlating definitions for difficult words in the app **/
        fun getDictionaryStrings(): Flow<List<DictionaryStrings>> {
            //returns alphabetized list
            return flowOf(
                listOf(
                    //#1
                    DictionaryStrings(
                        "Cretaceous",
                        "Name of the time period on Earth between 145 to 66 million years ago."
                    ),
                    //#2
                    DictionaryStrings(
                        "Tropical",
                        "An area that is usually humid, hot, and full of plants."
                    ),
                    //#3
                    DictionaryStrings(
                        "Habitat",
                        "The place where an animal lives."
                    ),
                    //#4
                    DictionaryStrings(
                        "Paleontologist",
                        "Scientist that studies the history of the Earth with fossils."
                    ),
                    //#5
                    DictionaryStrings(
                        "Predator",
                        "An animal that lives by killing or eating other animals."
                    ),
                    //#6
                    DictionaryStrings(
                        "Herbivore",
                        "An animal that eats only plants."
                    ),
                    //#7
                    DictionaryStrings(
                        "Club",
                        "A round and bony object at the end of the tail."
                    ),
                    //#8
                    DictionaryStrings(
                        "Fossil",
                        "Any remains of a dead animal like bones and shells."
                    ),
                    //#9
                    DictionaryStrings(
                        "Montana",
                        "Unimportant state located in the United States located just under Canada."
                    ),
                    //#10
                    DictionaryStrings(
                        "Laramidia",
                        "A giant island continent that existed during the Late Cretaceous period. Today Laramidia would stretch from Alaska to Mexico."
                    ),
                    //#11
                    DictionaryStrings(
                        "Discover",
                        "Find something new."
                    )
                ).sortedBy { words ->
                    words.word
                }
            )
        }
    }
}

