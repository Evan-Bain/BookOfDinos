package com.example.dinoappv2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databinding.ActivityDinoArticleBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import com.example.dinoappv2.viewModels.DinoArticleViewModelFactory
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class DinoArticleActivity : AppCompatActivity() {

    lateinit var binding: ActivityDinoArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDinoArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this

        //creating viewModel
        val viewModelFactory = DinoArticleViewModelFactory(
            DinosaurEncyclopediaDatabase.getInstance(this).dinosaurEncyclopediaDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DinoArticleViewModel::class.java)

        //setting dino title to the correlating data from the recycler view item pressed
        val dino = CompanionObject.allDinos!![CompanionObject.dinoArticleSelected!!]
        binding.dinoTitle.text = getString(dino.dinosaurKey)
        binding.dinoArticleImage.setImageResource(dino.badge)

        //setting dino article to the correlating text for the dinosaur
        val articlesArray: Array<out String> = resources.getStringArray(R.array.dinosaur_articles)
        //binding.dinoArticle.text = articlesArray[CompanionObject.dinoArticleSelected!!]
        //ADD THIS ^^^
        binding.dinoArticle.text = resources.getString(R.string.TRexOverview)
        //DELETE THIS ^^^
        //set quiz button enabled or not
        viewModel.quizButtonEnabled.observe(this, {
            binding.quizButton.isEnabled = it
        })

        //set whether or not quiz nav host is visible
        viewModel.quizVisible.observe(this, {
            binding.quizNavHost.visibility = if(it) {
                 View.VISIBLE
            } else {
                View.INVISIBLE
            }
        })

        viewModel.articleBodyAlpha.observe(this, {
            binding.dinoArticle.alpha = if(it) {
                1F
            } else {
                .01F
            }
        })
    }

    //used in quiz button onClick data binding to display quiz and reset variables
    //from last instance
    fun quizOnClick() {
        with(viewModel) {
            setNextButtonClicked(1)
            setAnswersCorrect(0)
            setQuizVisible(true)
            setQuizButton(false)
            setArticleBodyAlpha(false)
        }
    }

    //viewModel visible to quizFragment
    companion object {
        lateinit var viewModel: DinoArticleViewModel
    }
}