package com.example.dinoappv2.dataClasses

data class DictionaryStrings(
    val word: String,
    val definition: String
) {
    //adds the dictionary words to Dictionary Strings and sorts them alphabetically
    fun addStrings() {
        if(dictionaryStrings.size == 0) {
            val sortingArray = ArrayList<String>()
            dictionaryStrings.add(DictionaryStrings(
                    "Cretaceous",
                    "Name of the time period on Earth between 145 to 66 million years ago."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Tropical",
                "An area that is usually humid, hot, and full of plants."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Habitat",
                "The place where an animal lives."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Paleontologist",
                "Scientist that studies the history of the Earth with fossils."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Predator",
                "An animal that lives by killing or eating other animals."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Herbivore",
                "An animal that eats only plants."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Club",
                "A round and bony object at the end of the tail."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Fossil",
                "Any remains of a dead animal like bones and shells."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Montana",
                "Unimportant state located in the United States located just under Canada."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Laramidia",
                "A giant island continent that existed during the Late Cretaceous period. Today Laramidia would stretch from Alaska to Mexico."
            ))
            dictionaryStrings.add(DictionaryStrings(
                "Discover",
                "Find something new."
            ))
            for(i in 0 until dictionaryStrings.size) {
                sortingArray.add(dictionaryStrings[i].word)
            }
            sortingArray.sort()
            val originalSize = dictionaryStrings.size
            for(i in 0 until originalSize) {
                for(x in 0 until originalSize) {
                    if(dictionaryStrings[x].word == sortingArray[i]) {
                        dictionaryStrings.add(dictionaryStrings[x])
                        dictionaryStrings.removeAt(x)
                    }
                }
            }
        }

    }

    companion object {
        //variable used to get dictionary words in Dictionary Strings
        val dictionaryStrings = ArrayList<DictionaryStrings>()
    }
}

