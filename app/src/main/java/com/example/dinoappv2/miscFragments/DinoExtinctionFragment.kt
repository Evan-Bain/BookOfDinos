package com.example.dinoappv2.miscFragments

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.dataClasses.DictionaryStrings
import com.example.dinoappv2.databinding.FragmentDinoExtinctionBinding
import com.example.dinoappv2.viewModels.MainViewModel
import com.google.android.material.transition.MaterialSharedAxis
import java.util.*

class DinoExtinctionFragment : Fragment() {

    private lateinit var binding: FragmentDinoExtinctionBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dino_extinction,
            container,
            false
        )

        //set spannable strings for each section of info to allow difficult words to be pressed
        val extinctionTextSpan =
            SpannableString(resources.getString(R.string.extinction_general_text))
        val fastChaosTextSpan =
            SpannableString(resources.getString(R.string.fast_chaos_text))
        val slowChaosTextSpan =
            SpannableString(resources.getString(R.string.slow_chaos_text))
        val lifeFindsTextSpan =
            SpannableString(resources.getString(R.string.life_finds_text))


        val dictionaryList = mutableListOf<DictionaryStrings>()
        for ((i, word) in resources.getStringArray(R.array.dictionary_words).withIndex()) {
            dictionaryList.add(
                DictionaryStrings(
                    word,
                    resources.getStringArray(R.array.dictionary_definitions)[i]
                )
            )
        }
        for (element in dictionaryList) {
            val word = element.word.lowercase(Locale.getDefault())
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    //navigate to the dictionary and display definition of the word
                    exitTransition =
                        MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
                            duration = 750
                        }
                    val bundle = bundleOf("selectedWord" to word)
                    sharedViewModel.setDictionaryWord(true)
                    findNavController().navigate(
                        R.id.dictionary_bottom_nav,
                        bundle,

                        //set home screen to top of backStack to keep
                        //consistent user navigation (and not break bottomNav)
                        NavOptions.Builder().apply {
                            setPopUpTo(R.id.home_bottom_nav, true)
                        }.build()
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    //set style of clickable words to bolded with the secondary color
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.isFakeBoldText = true
                }
            }

            //find words that are in the dictionary in the info section
            if (extinctionTextSpan.contains(word)) {
                val first = extinctionTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = extinctionTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                extinctionTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (slowChaosTextSpan.contains(word)) {
                val first = slowChaosTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = slowChaosTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                slowChaosTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (fastChaosTextSpan.contains(word)) {
                val first = fastChaosTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = fastChaosTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                fastChaosTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (lifeFindsTextSpan.contains(word)) {
                val first = lifeFindsTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = lifeFindsTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                lifeFindsTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        with(binding) {
            extinctionText.text = extinctionTextSpan
            extinctionText.movementMethod = LinkMovementMethod.getInstance()
            fastChaosText.text = slowChaosTextSpan
            fastChaosText.movementMethod = LinkMovementMethod.getInstance()
            slowChaosText.text = fastChaosTextSpan
            slowChaosText.movementMethod = LinkMovementMethod.getInstance()
            lifeFindsText.text = lifeFindsTextSpan
            lifeFindsText.movementMethod = LinkMovementMethod.getInstance()
        }

        return binding.root
    }
}