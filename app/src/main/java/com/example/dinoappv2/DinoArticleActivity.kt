package com.example.dinoappv2

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModelProvider
import com.example.dinoappv2.bottomNav.BottomNavActivity
import com.example.dinoappv2.companionObjects.CompanionObject
import com.example.dinoappv2.dataClasses.DictionaryStrings
import com.example.dinoappv2.dataClasses.DinosaurArticleStrings
import com.example.dinoappv2.databases.DinosaurEncyclopediaDatabase
import com.example.dinoappv2.databinding.ActivityDinoArticleBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import com.example.dinoappv2.viewModels.DinoArticleViewModelFactory
import java.util.*


class DinoArticleActivity : AppCompatActivity() {

    lateinit var binding: ActivityDinoArticleBinding

    private val position = CompanionObject.dinoArticleSelected!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDinoArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this

        setSupportActionBar(binding.dinoArticleToolbar)

        binding.dinoArticleToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        //creating viewModel
        val viewModelFactory = DinoArticleViewModelFactory(
            DinosaurEncyclopediaDatabase.getInstance(this).dinosaurEncyclopediaDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DinoArticleViewModel::class.java)

        binding.viewModel = viewModel

        val habitatArticle = SpannableString(DinosaurArticleStrings(position).getDinoStrings()[1]?.get(0))
        val evolutionArticle = SpannableString(DinosaurArticleStrings(position).getDinoStrings()[2]?.get(0))
        val fossilArticle = SpannableString(DinosaurArticleStrings(position).getDinoStrings()[3]?.get(0))
        DictionaryStrings("","").addStrings()

        for(i in 0 until DictionaryStrings.dictionaryStrings.size) {
            val word = DictionaryStrings.dictionaryStrings[i].word.lowercase(Locale.getDefault())
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    CompanionObject.transitionToDictionary = true
                    CompanionObject.wordClicked = word
                    startActivity(Intent(applicationContext, BottomNavActivity::class.java))
                }
            }
            if(habitatArticle.contains(word)) {
                val first = habitatArticle.indexOf(word)
                val last = word.length
                habitatArticle.setSpan(clickableSpan, first, first+last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            if(evolutionArticle.contains(word)) {
                val first = evolutionArticle.indexOf(word)
                val last = word.length
                evolutionArticle.setSpan(clickableSpan, first, first+last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            if(fossilArticle.contains(word)) {
                val first = fossilArticle.indexOf(word)
                val last = word.length
                fossilArticle.setSpan(clickableSpan, first, first+last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        viewModel.habitatDroppedDown.observe(this, {
            if(it) {
                binding.habitatText.visibility = View.VISIBLE
                binding.habitatDropButton.setImageResource(R.drawable.drop_down_arrow_up)
            } else {
                binding.habitatText.visibility = View.GONE
                binding.habitatDropButton.setImageResource(R.drawable.drop_down_arrow_down)
            }
        })

        viewModel.evolutionDroppedDown.observe(this, {
            if(it) {
                binding.crazyEvolutionText.visibility = View.VISIBLE
                binding.crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_up)
            } else {
                binding.crazyEvolutionText.visibility = View.GONE
                binding.crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_down)
            }
        })

        viewModel.fossilDroppedDown.observe(this, {
            if(it) {
                binding.fossilHistoryText.visibility = View.VISIBLE
                binding.fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_up)
            } else {
                binding.fossilHistoryText.visibility = View.GONE
                binding.fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_down)
            }
        })

        //setting dino title to the correlating data from the recycler view item pressed
        val dino = CompanionObject.allDinos!![CompanionObject.dinoArticleSelected!!]
        binding.dinoTitle.text = getString(dino.dinosaurKey)
        binding.dinoArticleImage.setImageResource(dino.badge)

        //setting dino article to the correlating text for the dinosaur
        val articlesArray: Array<out CharSequence> = resources.getTextArray(R.array.quick_facts)
        //binding.quickFacts.text = articlesArray[position]
        //ADD THIS ^^^
        //binding.quickFacts.text = resources.getString(R.string.TRexOverview)
        //DELETE THIS ^^^

        with(binding) {
            val dinosaurStrings = DinosaurArticleStrings(position).getDinoStrings()
            heightFactText.text = dinosaurStrings[0]!![0]
            heightFactText.movementMethod = LinkMovementMethod.getInstance()
            weightFactText.text = dinosaurStrings[0]!![1]
            livedFactText.text = dinosaurStrings[0]!![2]
            speedFactText.text = dinosaurStrings[0]!![3]
            nameFactText.text = dinosaurStrings[0]!![4]
            habitatText.text = habitatArticle
            habitatText.movementMethod = LinkMovementMethod.getInstance()
            crazyEvolutionText.text = evolutionArticle
            crazyEvolutionText.movementMethod = LinkMovementMethod.getInstance()
            fossilHistoryText.text = fossilArticle
            fossilHistoryText.movementMethod = LinkMovementMethod.getInstance()
        }
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