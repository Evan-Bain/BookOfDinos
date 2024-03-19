package com.example.dinoappv2.home.dinoEras

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.dictionary.model.DictionaryStrings
import com.example.dinoappv2.databinding.FragmentDinoErasBinding
import com.example.dinoappv2.main.viewModel.MainViewModel
import com.google.android.material.transition.MaterialSharedAxis
import java.util.*

class DinoErasFragment : Fragment() {

    private lateinit var binding: FragmentDinoErasBinding

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
            R.layout.fragment_dino_eras,
            container,
            false
        )

        //set spannable strings for each section of info to allow difficult words to be pressed
        val mesozoicTextSpan =
            SpannableString(resources.getString(R.string.mesozoic_info_text))
        val triassicTextSpan =
            SpannableString(resources.getString(R.string.triassic_info_text))
        val jurassicTextSpan =
            SpannableString(resources.getString(R.string.jurassic_info_text))
        val cretaceousTextSpan =
            SpannableString(resources.getString(R.string.cretaceous_info_text))


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
            if (mesozoicTextSpan.contains(word)) {
                val first = mesozoicTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = mesozoicTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                mesozoicTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (triassicTextSpan.contains(word)) {
                val first = triassicTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = triassicTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                triassicTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (jurassicTextSpan.contains(word)) {
                val first = jurassicTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = jurassicTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                jurassicTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (cretaceousTextSpan.contains(word)) {
                val first = cretaceousTextSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = cretaceousTextSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                cretaceousTextSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        with(binding) {
            mesozoicText.text = mesozoicTextSpan
            binding.mesozoicText.movementMethod = LinkMovementMethod.getInstance()
            triassicText.text = triassicTextSpan
            binding.triassicText.movementMethod = LinkMovementMethod.getInstance()
            jurassicText.text = jurassicTextSpan
            binding.jurassicText.movementMethod = LinkMovementMethod.getInstance()
            cretaceousText.text = cretaceousTextSpan
            binding.cretaceousText.movementMethod = LinkMovementMethod.getInstance()
        }

        return binding.root
    }
}