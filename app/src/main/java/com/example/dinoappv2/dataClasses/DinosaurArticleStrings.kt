package com.example.dinoappv2.dataClasses

class DinosaurArticleStrings(val position: Int) {

    fun getDinoStrings(): HashMap<Int, List<String>> {
        return when(position) {
            0 -> getAnkylosaurusArticleStrings()
            1 -> getBrontosaurusArticleStrings()
            else -> getAnkylosaurusArticleStrings()
        }
    }

    private fun getBrontosaurusArticleStrings(): HashMap<Int, List<String>> {
        val articleStrings = HashMap<Int, List<String>>()
        articleStrings[0] = listOf(
            "27.8 feet",
            "67,000 pounds",
            "145 million years ago",
            "19 miles an hour",
            "Thunder lizard"
        )
        articleStrings[1] = listOf(
            "Brontosaurus was a very large dinosaur that would eat leaves from trees that were too tall for other dinosaurs to reach. The plants Brontosaurus ate were usually hard to swallow so this dinosaur ate rocks to help digest these plants. Even though Brontosaurus was very tall it still had many predators, one of these predators may have been Allosaurus."        )
        articleStrings[2] = listOf(
            "The long neck!\nOne of the craziest things about Brontosaurus was its long neck. This neck was so big that Brontosaurus also needed a long tail to help balance itself so it wouldn’t fall over because of its neck. Today, the animal with the longest neck is the giraffe which has a neck size of up to 6 feet. 6 feet isn’t even close to Brontosaurus which could have had a neck size of 50 feet!"
        )
        articleStrings[3] = listOf(
            "The first fossil of Brontosaurus was found in the USA during the 1870s. Unfortunately, during the early 1900s paleontologists noticed that Brontosaurus may not be a new dinosaur. They thought that Brontosaurus was an already discovered dinosaur called Apatosaurus. Today it is still unknown if Brontosaurus is a real dinosaur so it’s all up for you to decide!"        )
        return articleStrings
    }

    private fun getAnkylosaurusArticleStrings(): HashMap<Int, List<String>> {
        val articleStrings = HashMap<Int, List<String>>()
        articleStrings[0] = listOf(
            "5.6 feet",
            "18,000 pounds",
            "66 million years ago",
            "6 miles an hour",
            "Fused lizard"
        )
        articleStrings[1] = listOf(
            "Ankylosaurus lived during the late Cretaceous period in a warm, tropical habitat. Paleontologists guess that this dinosaur had very few predators, but if Ankylosaurus did have any predators the mighty T-Rex could have been one. As scary as Ankylosaurus looked, it was actually an herbivore that ate plants that were close to the ground; however, these plants were usually small and not big."
        )
        articleStrings[2] = listOf(
            "The mighty club!\nThe club at the end of Ankylosaurus’ tail is definitely the craziest thing about this dinosaur. The club was so strong it could easily shatter the bones of another dinosaur. Unfortunately, today there are no animals that have clubs on their tails."
        )
        articleStrings[3] = listOf(
            "The first fossil of Ankylosaurus was found in 1906 by a paleontologist named Barnum Brown. This fossil was found in Montana but during the Cretaceous period this place would’ve been the continent known as Laramidia. Only three fossils of Ankylosaurus have ever been found and not enough bones have been found to create a complete fossil."
        )
        return articleStrings
    }

}