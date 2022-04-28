package com.example.dinoappv2.dataClasses

class DinosaurArticleStrings(val position: Int) {

    //returns the proper data depending to the recycler view item that was clicked
    fun getDinoStrings(): HashMap<Int, List<String>> {
        return when(position) {
            0 -> getAnkylosaurusArticleStrings()
            1 -> getBrontosaurusArticleStrings()
            2 -> getDilophosaurusArticleStrings()
            3 -> getMosasaurusArticleStrings()
            4 -> getPteranodonArticleStrings()
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

    private fun getDilophosaurusArticleStrings(): HashMap<Int, List<String>> {
        val articleStrings = HashMap<Int, List<String>>()
        articleStrings[0] = listOf(
            "6 feet",
            "900 pounds",
            "182 million years ago",
            "23.5 miles an hour",
            "Two-crested lizard"
        )
        articleStrings[1] = listOf(
            "This dinosaur was the largest land animal during the entire time it walked the Earth. Dilophosaurus could be found hunting in rainforests and swamps. The animals that Dilophosaurus would hunt were most likely smaller, herbivore dinosaurs and maybe even fish too."        )
        articleStrings[2] = listOf(
            "The crests on its head!\nThe crests on Dilophosaurus are a mystery to palaeontologists because it is unknown what the purpose for these crests were. However, some palaeontologists believe that the crests were used to attract females. The crests were also very weak and could not be used in fighting."
        )
        articleStrings[3] = listOf(
            "The first fossil of Dilophosaurus was found in 1942 by Sam Welles in Arizona. This fossil that was found did not have the head of Dilophosaurus so the dinosaur was named ‘megalosaurus wetherilli’ and not dilophosaurus. However, in 1970 the head of Dilophosaurus was found which led to the name being changed from ‘megalosaurus wetherilli’ to dilophosaurus."
        )
        return articleStrings
    }

    private fun getMosasaurusArticleStrings(): HashMap<Int, List<String>> {
        val articleStrings = HashMap<Int, List<String>>()
        articleStrings[0] = listOf(
            "56 feet",
            "30,000 pounds",
            "66 million years ago",
            "30 miles an hour",
            "Two-crested lizard"
        )
        articleStrings[1] = listOf(
            "Mosasaurus lived in the oceans hunting almost anything including sharks, plesiosaurs, and even other mosasaurs. Mosasaurus was the top predator of the oceans during its time meaning that it would mainly only be eaten by other mosasaurs."
        )
        articleStrings[2] = listOf(
            "The teeth layout!\nMosasaurus was massive compared to all the other animals in the sea meaning that this reptile had almost no competition, but it also had another surprising feature. Mosasaurus had two rows of teeth on the top jaw. The second row of teeth was behind the was found further back in the long snout of Mosasaurus and had smaller teeth than the front. This second row of teeth was used to hold on to prey that were struggling."
        )
        articleStrings[3] = listOf(
            "The first fossil of Mosasaurus was found in the Mouse River in 1764. This is how Mosasaurus got its name, ‘Lizard of the Mouse River’. However, when the fossil of Mosaurus was first found it was believed to be a whale. Palaeontologists can tell a lot from the fossils found of Mosasaurus, they have even found out that Mosasaurus is not a dinosaur and instead a reptile. It is believed that Mosasaurus is closely related to snakes and monitor lizards."
        )
        return articleStrings
    }

    private fun getPteranodonArticleStrings(): HashMap<Int, List<String>> {
        val articleStrings = HashMap<Int, List<String>>()
        articleStrings[0] = listOf(
            "33 feet",
            "110 pounds",
            "85 million years ago",
            "80 miles an hour",
            "Winged and toothless"
        )
        articleStrings[1] = listOf(
            "When Pteranodon was alive it could be found flying along an ocean channel called the Western Interior Seaway. The Western Interior Seaway separated Laramidia from Appalachia. Palaeontologists believe Pteranodon flew in the air like a large bird today. Pteranodon stayed around this ocean channel because one of the main things Pteranodon ate was fish."
        )
        articleStrings[2] = listOf(
            "The huge wing size!\nThe wing size (also called ‘wingspan’) could reach up to 33 feet long. The wingspan of Pteranodon is so long that the bird with the longest wingspan alive today is 12 feet. Pteranodon’s wingspan is almost 3 times that size! Palaeontologists believe that since Pteranodon’s wingspan was so long Pteranodon would glide a lot instead of flapping its wings a lot."
        )
        articleStrings[3] = listOf(
            "The first fossil of Pteranodon was found in 1871 by Othniel Charles Marsh in Kansas, but fossils have also been found in Europe and not just America. The fossils found of Pteranodon have shown Paleontologists that Pteranodon was actually not a dinosaur but instead a huge flying reptile."
        )
        return articleStrings
    }

}