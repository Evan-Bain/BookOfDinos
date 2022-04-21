package com.example.dinoappv2

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.dinoappv2.bottomNav.BottomNavActivity
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DictionaryStrings
import com.example.dinoappv2.dataClasses.DinosaurArticleStrings
import com.example.dinoappv2.dataClasses.DinosaurEncyclopedia
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databinding.ActivityDinoArticleBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import com.example.dinoappv2.viewModels.DinoArticleViewModelFactory
import kotlinx.coroutines.launch
import java.util.*


class DinoArticleActivity : AppCompatActivity() {

    lateinit var binding: ActivityDinoArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDinoArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this

        setSupportActionBar(binding.dinoArticleToolbar)

        val dinoSelected = intent.extras?.get("dinoSelected") as DinosaurEncyclopedia

        binding.dinoArticleToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        //creating viewModel
        val viewModelFactory = DinoArticleViewModelFactory(
            DinosaurEncyclopediaDatabase.getInstance(this).dinosaurEncyclopediaDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DinoArticleViewModel::class.java)

        binding.viewModel = viewModel

        val dinoPosition = dinoSelected.position
        val habitatArticle = SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[1]?.get(0))
        val evolutionArticle = SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[2]?.get(0))
        val fossilArticle = SpannableString(DinosaurArticleStrings(dinoPosition).getDinoStrings()[3]?.get(0))

        lifecycleScope.launch {
            DictionaryStrings.getDictionaryStrings()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    for(element in it) {
                        val word = element.word.lowercase(Locale.getDefault())
                        val clickableSpan: ClickableSpan = object : ClickableSpan() {
                            override fun onClick(widget: View) {
                                CompanionObject.transitionToDictionary = true
                                CompanionObject.wordClicked = word
                                startActivity(Intent(applicationContext, BottomNavActivity::class.java))
                            }
                        }
                        if(habitatArticle.contains(word)) {
                            val first = habitatArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while(whileLoop) {
                                val newWord = habitatArticle[first + iterator].toString()
                                if(newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?") {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            habitatArticle.setSpan(clickableSpan, first, first+iterator, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                        if(evolutionArticle.contains(word)) {
                            val first = evolutionArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while(whileLoop) {
                                val newWord = evolutionArticle[first + iterator].toString()
                                if(newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?") {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            evolutionArticle.setSpan(clickableSpan, first, first+iterator, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                        if(fossilArticle.contains(word)) {
                            val first = fossilArticle.indexOf(word)
                            var whileLoop = true
                            var iterator = 0
                            while(whileLoop) {
                                val newWord = fossilArticle[first + iterator].toString()
                                if(newWord == " " || newWord == "." || newWord == ","
                                    || newWord == "!" || newWord == "?") {
                                    whileLoop = false
                                } else {
                                    iterator++
                                }
                            }
                            fossilArticle.setSpan(clickableSpan, first, first+iterator, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    }
                    with(binding) {
                        habitatText.text = habitatArticle
                        crazyEvolutionText.text = evolutionArticle
                        fossilHistoryText.text = fossilArticle
                    }
                }
        }

        viewModel.habitatDroppedDown.observe(this) {
            if (it) {
                binding.habitatText.visibility = View.VISIBLE
                binding.habitatDropButton.setImageResource(R.drawable.drop_down_arrow_up)
            } else {
                binding.habitatText.visibility = View.GONE
                binding.habitatDropButton.setImageResource(R.drawable.drop_down_arrow_down)
            }
        }

        viewModel.evolutionDroppedDown.observe(this) {
            if (it) {
                binding.crazyEvolutionText.visibility = View.VISIBLE
                binding.crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_up)
            } else {
                binding.crazyEvolutionText.visibility = View.GONE
                binding.crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_down)
            }
        }

        viewModel.fossilDroppedDown.observe(this) {
            if (it) {
                binding.fossilHistoryText.visibility = View.VISIBLE
                binding.fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_up)
            } else {
                binding.fossilHistoryText.visibility = View.GONE
                binding.fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_down)
            }
        }

        //setting dino title to the correlating data from the recycler view item pressed
        binding.dinoTitle.text = dinoSelected.name
        binding.dinoArticleImage.setImageResource(dinoSelected.badge)

        with(binding) {
            val dinosaurStrings = DinosaurArticleStrings(dinoPosition).getDinoStrings()
            heightFactText.text = dinosaurStrings[0]!![0]
            heightFactText.movementMethod = LinkMovementMethod.getInstance()
            weightFactText.text = dinosaurStrings[0]!![1]
            livedFactText.text = dinosaurStrings[0]!![2]
            speedFactText.text = dinosaurStrings[0]!![3]
            nameFactText.text = dinosaurStrings[0]!![4]
            habitatText.movementMethod = LinkMovementMethod.getInstance()
            crazyEvolutionText.movementMethod = LinkMovementMethod.getInstance()
            fossilHistoryText.movementMethod = LinkMovementMethod.getInstance()
        }
        //set quiz button enabled or not
        viewModel.quizButtonEnabled.observe(this) {
            binding.quizButton.isEnabled = it
        }

        //set whether or not quiz nav host is visible
        viewModel.quizVisible.observe(this) {
            binding.quizNavHost.visibility = if (it) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }

        /*viewModel.articleBodyAlpha.observe(this, {
            binding.quickFacts.alpha = if(it) {
                1F
            } else {
                .01F
            }
        })*/
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